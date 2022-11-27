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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service(value = "PointService")
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    public Optional<PointHistory> findPointHistory(Long pointSeq){
        PointHistory pointHistory = pointRepository.findById(pointSeq)
                .orElseThrow(() -> new PointHistoryException("포인트 이력을 찾을수 없습니다."));
        return Optional.ofNullable(pointHistory);
    }

    public List<PointHistory> findPointHistoryAll(String date) {
        LocalDateTime searchDate = Utils.parseDateTime(date);
        if(Objects.isNull(searchDate)) throw new PointHistoryException("요청 파라미터 값을 확인해주세요.( 형식 : YYYYMMDD)");
        List<PointHistory> pointHistories = pointRepository.findAllByRegDateIsBeforeOrderByPointSeq(searchDate);
        if(Objects.isNull(pointHistories)) throw new PointHistoryException("해당 날짜에 포인트 지급 이력이 존재하지 않습니다.");
        return pointHistories;
    }

    // 사용자 별 오늘 포인트
    private Long findPointHistoryByUser(Long userSeq) {
        return pointRepository.countAllByRegDateAndUserSeq(LocalDate.now(), userSeq);
    }


    public void addPoint(PointRequest pointRequest){
        pointRepository.save(PointHistory.builder()
                .userSeq(pointRequest.getUserSeq())
                .regDate(pointRequest.getRegDate())
                .pointLevel(Point.POINT_1DAY.getDays())
                .point(Point.POINT_1DAY.getPoint())
                .build());
    }



}
