package com.musinsa.homework.repository;


import com.musinsa.homework.model.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PointRepository extends JpaRepository<PointHistory, Long> {

    List<PointHistory> findAllByRegDateOrderByPointSeqDesc(LocalDate regDate);
    List<PointHistory> findAllByRegDateOrderByPointSeq(LocalDate regDate);

    Integer countAllByRegDate(LocalDate regDate);

    List<PointHistory> findAllByRegDateBetweenAndUserSeq(LocalDate agoDate, LocalDate today, Long userSeq);

    PointHistory findByRegDateAndUserSeq(LocalDate date, Long userSeq);

}
