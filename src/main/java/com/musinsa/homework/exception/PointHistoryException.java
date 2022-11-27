package com.musinsa.homework.exception;

import lombok.Getter;


@Getter
public class PointHistoryException extends RuntimeException {

    public PointHistoryException(String message) {
        super(message);
    }


}
