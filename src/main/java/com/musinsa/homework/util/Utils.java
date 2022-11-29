package com.musinsa.homework.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;


@Getter
@Slf4j
public class Utils {

    public static LocalDate convertLocalDate(String date){
        LocalDate localDate;
        try {
            localDate = DateTimeFormatter.ofPattern("yyyyMMdd").parse(date, LocalDate::from);
        } catch(Exception e) {
            localDate = null;
        }
        return localDate;
    }





}
