package com.musinsa.homework.service;

import com.musinsa.homework.dto.PointRequest;
import com.musinsa.homework.enumClass.Point;
import com.musinsa.homework.model.PointHistory;
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

    /**
     * 포인트를 지급 요청
     *
     */
    public void addPoint(PointRequest pointRequest){
        // step 1. 오늘 10명 마감되었을 경우 튕겨낸다.
        if( pointRepository.countAllByRegDate(pointRequest.getRegDate()) > 10) {
            log.info("금일 선착순 이벤트가 마감되었습니다. 내일 다시 시도해주세요");
            throw new PointHistoryException("금일 선착순 이벤트가 마감되었습니다. 내일 다시 시도해주세요");
        }
        // step 1. 오늘 이미 지급된 사용자일 경우 튕겨낸다.
        if(Objects.nonNull(this.todayPointHistoryUser(pointRequest.getUserSeq()))){
            log.info("오늘은 이미 지급되었습니다. 내일 다시 시도해주세요");
            throw new PointHistoryException("오늘은 이미 지급되었습니다. 내일 다시 시도해주세요");
        } else {
            // step 3. userSeq 로 최근 10일자 포인트 지급 이력을 가져온다.
            List<PointHistory> userHistories = this.lateDaysUserHistory(pointRequest.getUserSeq(), POINT_10DAYS.getDays());
            // step 4. 있을 경우 각 조건에 따라 차등 지급
            if (!CollectionUtils.isEmpty(userHistories) ) {
                if(userHistories.size() == POINT_10DAYS.getDays()) {
                    this.savePoint(pointRequest, POINT_10DAYS);
                } else if(this.lateDaysUserHistory(pointRequest.getUserSeq(), POINT_5DAYS.getDays()).size() == POINT_5DAYS.getDays()) {
                    this.savePoint(pointRequest, POINT_5DAYS);
                } else if(this.lateDaysUserHistory(pointRequest.getUserSeq(), POINT_3DAYS.getDays()).size() == POINT_3DAYS.getPoint()) {
                    this.savePoint(pointRequest, POINT_3DAYS);
                } else
                    this.savePoint(pointRequest, POINT_1DAY);
            } else
                this.savePoint(pointRequest, Point.POINT_1DAY);
        }

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

    // 사용자 오늘 포인트 이력
    private PointHistory todayPointHistoryUser(Long userSeq) {
        return pointRepository.findByRegDateAndUserSeq(LocalDate.now(), userSeq);
    }

    // 사용자의 최근 10일 포인트 이력
    private List<PointHistory> lateDaysUserHistory(Long userSeq, int days) {
        return pointRepository.findAllByRegDateBetweenAndUserSeq
                (LocalDate.now().minusDays(days), LocalDate.now(),userSeq);
    }

    // 포인트 이력 저장
    private void savePoint(PointRequest pointRequest, Point point) {
        pointRepository.save(PointHistory.builder()
                .userSeq(pointRequest.getUserSeq())
                .regDate(Objects.nonNull(pointRequest.getRegDate()) ? pointRequest.getRegDate() : LocalDate.now())
                .pointLevel(point.getDays())
                .point(point.getPoint())
                .build());
    }

}
