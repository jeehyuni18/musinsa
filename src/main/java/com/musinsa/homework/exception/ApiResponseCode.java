package com.musinsa.homework.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResponseCode implements EnumApiResponseType {
    OK("요청이 성공하였습니다.")
    , BAD_PARAMETER("요청 파라미터가 잘못되었습니다.")
    , NOT_FOUND_POINT_HISTORY("해당 포인트 이력을 찾을 수 없습니다.")
    ;
    private final String message;

    public String getId() {
        return name();
    }

    @Override
    public String getText() {
        return message;
    }
}
