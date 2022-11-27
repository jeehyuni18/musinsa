package com.musinsa.homework.enumClass;

import lombok.Getter;

public enum Point {

    POINT_1DAY(100,1, "매일 00 시 00 분 00 초 선착순 10 명 100 포인트 지급!!!"),
    POINT_3DAYS(300,3,"3일 연속 포인트 지급 이벤트 신청 보상 300포인트 지급!!!"),
    POINT_10DAYS(1000,10,"10일 연속 포인트 지급 이벤트 신청 보상 1000포인트 지급!!!");

    @Getter
    private int point;
    @Getter
    private int days;

    @Getter
    private String pointDescription;


    private Point(int point, int days, String pointDescription)  {
        this.point = point;
        this.days = days;
        this.pointDescription = pointDescription;
    }
}
