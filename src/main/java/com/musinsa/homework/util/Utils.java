package com.musinsa.homework.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


@Getter
@Slf4j
public class Utils {

    public static LocalDateTime parseDateTime(String date) {
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.of(convertLocalDate(date), LocalTime.of(23,59,59));
        } catch (Exception e){
            localDateTime = null;
        }
        return localDateTime;
    }


    private static LocalDate convertLocalDate(String date){
        LocalDate localDate;
        try {
            localDate = DateTimeFormatter.ofPattern("yyyyMMdd").parse(date, LocalDate::from);
        } catch(Exception e) {
            localDate = null;
        }
        return localDate;
    }





}
