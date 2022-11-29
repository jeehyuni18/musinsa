package com.musinsa.homework.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "point_history")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PointHistory {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long pointSeq;

    @Column(nullable = false)
    private Integer pointLevel;


    @Column(nullable = false)
    private Integer point;

    @Column(nullable = false)
    private Long userSeq;


    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate regDate;





    public void setPointLevel(Integer pointLevel) {
        this.pointLevel = pointLevel;
    }
}
