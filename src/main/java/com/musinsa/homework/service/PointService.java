package com.musinsa.homework.service;

import com.musinsa.homework.dto.PointRequest;
import com.musinsa.homework.enumClass.Point;
import com.musinsa.homework.model.PointHistory;
import com.musinsa.homework.model.UserEventCompleteLog;
import com.musinsa.homework.repository.UserEventCompleteLogRepository;
import com.musinsa.homework.util.Utils;
import com.musinsa.homework.exception.PointHistoryException;
import com.musinsa.homework.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.musinsa.homework.enumClass.Point.*;

@Slf4j
@Service(value = "PointService")
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    private final UserEventCompleteLogRepository userCompleteRepository;
    private static String MAX_PERSON_MESSAGE = "금일 선착순 이벤트가 마감되었습니다. 내일 다시 시도해주세요";
    private static String ALREADY_PAID_MESSAGE = "오늘 포인트가 이미 지급되었습니다. 내일 다시 시도해주세요";



    /**
     * 포인트 지급을 요청
     *
     */
    public String addPoint(PointRequest pointRequest) {
        // step 1. 오늘 10명 마감되었을 경우 튕겨낸다.
        if (pointRepository.countAllByRegDate(pointRequest.getRegDate()) >= 10) {
            return MAX_PERSON_MESSAGE;
        }
        // step 2. 오늘 이미 지급된 사용자일 경우 튕겨낸다.
        if (Objects.nonNull(this.todayPointHistoryUser(pointRequest.getUserSeq(), pointRequest.getRegDate()))) {
            return ALREADY_PAID_MESSAGE;
            // step 3. 최근 10일치 또는 10일치 보상 이후 데이터를 가져와 각 조건에 따라 포인트 지급
        } else {
            List<PointHistory> userHistories = this.baseDateUserHistory(pointRequest, POINT_10DAYS.getDays());
            if (CollectionUtils.isEmpty(userHistories)) {
                this.savePoint(pointRequest, POINT_1DAY);
                return POINT_1DAY.getPointDescription();
            } else if (isBefore3Level(userHistories)) { // 3일 연속 이전까지
                return this.validPointLevel(userHistories, pointRequest, POINT_3DAYS).getPointDescription();
            } else if (isBefore5Level(userHistories)) { // 5일 연속 이전까지
                return this.validPointLevel(userHistories, pointRequest, POINT_5DAYS).getPointDescription();
            } else if (isBefore10Level(userHistories)) { // 10일 연속 이전까지
                return this.validPointLevel(userHistories, pointRequest, POINT_10DAYS).getPointDescription();
            }
        }
        return null;
    }

    // 3일 연속 이전까지
    private boolean isBefore3Level(List<PointHistory> userHistories) {
        return userHistories.size() >= POINT_1DAY.getDays() && userHistories.size() <= POINT_3DAYS.getDays() -1;
    }
    // 5일 연속 이전까지
    private boolean isBefore5Level(List<PointHistory> userHistories) {
        return userHistories.size() >= POINT_3DAYS.getDays() && userHistories.size() <= POINT_5DAYS.getDays() -1;
    }

    // 10일 연속 이전까지
    private boolean isBefore10Level(List<PointHistory> userHistories){
       return userHistories.size() >= POINT_5DAYS.getDays() && userHistories.size() <= POINT_10DAYS.getDays() -1;
    }

    // 10일 연속 포인트 보상을 지급 받은 이력이 있는지 체크
    private UserEventCompleteLog checkUserCompleteEvent(Long userSeq) {
       UserEventCompleteLog userCompleted = userCompleteRepository.findFirstByUserSeqOrderByLastEventCompleteDateDesc(userSeq);
       return Objects.nonNull(userCompleted) ? userCompleted : null;
    }


    private Point validPointLevel(List<PointHistory> userHistories, PointRequest pointRequest, Point point){
        if (userHistories.size() < point.getDays() - 1) {
            this.savePoint(pointRequest, POINT_1DAY);
            return POINT_1DAY;
        } else if(userHistories.size() == point.getDays() - 1) {
            this.savePoint(pointRequest, point);
            // 10일 연속 보상 이면 지급 이력 저장
            if(point.equals(POINT_10DAYS)) this.saveUserEventCompleteLog(pointRequest);
        }
        return point;
    }



    // pointSeq로 상세 조회
    public Optional<PointHistory> findPointHistory(Long pointSeq){
        PointHistory pointHistory = pointRepository.findById(pointSeq)
                .orElseThrow(() -> new PointHistoryException("포인트 이력을 찾을수 없습니다."));
        return Optional.ofNullable(pointHistory);
    }

    // 날짜기준 포인트 지급 이력
    public List<PointHistory> findPointHistoryAll(String date, String order) {
        LocalDate searchDate = Utils.convertLocalDate(date);
        if(Objects.isNull(searchDate)) throw new PointHistoryException("요청 파라미터 값을 확인해주세요.( 형식 : YYYYMMDD)");
        List<PointHistory> pointHistories;
        if(order.equals("desc")) {
            pointHistories = pointRepository.findAllByRegDateOrderByPointSeqDesc(searchDate);
        } else pointHistories = pointRepository.findAllByRegDateOrderByPointSeq(searchDate);

        if(Objects.isNull(pointHistories)) throw new PointHistoryException("해당 날짜에 포인트 지급 이력이 존재하지 않습니다.");
        return pointHistories;
    }

    // 사용자 오늘 포인트 이력 (파라미터 date 값이 없을 때 오늘 날짜)
    private PointHistory todayPointHistoryUser(Long userSeq, LocalDate date) {
        return pointRepository.findByRegDateAndUserSeq(date, userSeq);
    }

    // 사용자의 최근 10일 또는 10일치 보상 이후 시점 포인트 이력
    private List<PointHistory> baseDateUserHistory(PointRequest pointRequest, int days) {
        UserEventCompleteLog userCompleteEvent = this.checkUserCompleteEvent(pointRequest.getUserSeq());
        LocalDate baseDate;
        if(Objects.isNull(userCompleteEvent))
            baseDate = pointRequest.getRegDate().minusDays(days);
        else
            baseDate = userCompleteEvent.getLastEventCompleteDate();
        return pointRepository.findAllByRegDateBetweenAndUserSeq(baseDate, pointRequest.getRegDate(), pointRequest.getUserSeq());
    }

    // 포인트 이력 저장
    private void savePoint(PointRequest pointRequest, Point point) {
        if(point.equals(POINT_1DAY)) {
            pointRepository.save(PointHistory.builder()
                    .userSeq(pointRequest.getUserSeq())
                    .regDate(pointRequest.getRegDate())
                    .pointLevel(point.getDays())
                    .point(point.getPoint())
                    .build());
        } else
            pointRepository.save(PointHistory.builder()
                    .userSeq(pointRequest.getUserSeq())
                    .regDate(pointRequest.getRegDate())
                    .pointLevel(point.getDays())
                    .point(point.getPoint() + POINT_1DAY.getPoint())
                    .build());

    }

    // 10일 연속 포인트 지급 보상 날짜 저장
    private void saveUserEventCompleteLog(PointRequest pointRequest){
        userCompleteRepository.save(UserEventCompleteLog.builder()
                .userSeq(pointRequest.getUserSeq())
                .lastEventCompleteDate(pointRequest.getRegDate())
                .build());
    }

}
