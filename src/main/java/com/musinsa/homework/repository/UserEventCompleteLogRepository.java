package com.musinsa.homework.repository;


import com.musinsa.homework.model.UserEventCompleteLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserEventCompleteLogRepository extends JpaRepository<UserEventCompleteLog, Long> {

   UserEventCompleteLog findFirstByUserSeqOrderByLastEventCompleteDateDesc(Long userSeq);

}
