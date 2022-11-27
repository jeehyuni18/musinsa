package com.musinsa.homework.exception;

import com.musinsa.homework.global.ResponseFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(annotations = { RestController.class })
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PointHistoryException.class)
    public ResponseFormat<String> NotFoundException(PointHistoryException e) {
        return ResponseFormat.createException(ApiResponseCode.NOT_FOUND_POINT_HISTORY, 422, e.getMessage());
    }

}
