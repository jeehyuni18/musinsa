package com.musinsa.homework.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "user_event_complete_log")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserEventCompleteLog {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long completeLogSeq;

    @Column(nullable = false)
    private Long userSeq;


    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate lastEventCompleteDate;

    
}
