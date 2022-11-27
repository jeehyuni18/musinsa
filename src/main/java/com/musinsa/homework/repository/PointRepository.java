package com.musinsa.homework.repository;


import com.musinsa.homework.model.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<PointHistory, Long> {
    List<PointHistory> findAllByRegDateIsBeforeOrderByPointSeq(LocalDateTime regDate);

    Long countByUserSeq(Long userSeq);

    Long countAllByRegDateAndUserSeq(LocalDate regDate, Long userSeq);


}
