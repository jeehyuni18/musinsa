package com.musinsa.homework.global;

import lombok.Getter;

public class Constants {

    public enum Keys {

        CODE("code"),
        STATUS("status"),
        MESSAGE("message"),
        DETAIL("detail"),
        TITLE("title");

        @Getter
        private String key;

        Keys(String key) {
            this.key = key;
        }
    }

    public enum Code {
        SUCCESS(0, "SUCCESS");

        @Getter
        private int code;

        @Getter
        private String message;

        Code(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }


}