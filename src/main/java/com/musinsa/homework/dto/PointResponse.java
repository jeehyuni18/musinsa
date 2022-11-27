package com.musinsa.homework.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ToString
public class PointResponse {

    private String description;

    public LocalDateTime settingRequestDate(LocalDate date) {
        LocalDateTime resDate = LocalDateTime.of(date, LocalTime.of(00,00,00));
        return resDate;
    }

}
