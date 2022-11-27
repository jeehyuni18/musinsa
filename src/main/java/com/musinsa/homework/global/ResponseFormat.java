package com.musinsa.homework.global;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.musinsa.homework.exception.ApiResponseCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.logging.LogLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class ResponseFormat<S> {

    private Map<String, Object> meta = new HashMap<>();
    private Object data;

    public ResponseFormat() {
        meta.put(Constants.Keys.CODE.getKey(), Constants.Code.SUCCESS.getCode());
        meta.put(Constants.Keys.MESSAGE.getKey(), Constants.Code.SUCCESS.getMessage());

        this.data = new EmptyResultVo();
    }

    public ResponseFormat(Object data) {
        meta.put(Constants.Keys.CODE.getKey(), Constants.Code.SUCCESS.getCode());
        meta.put(Constants.Keys.MESSAGE.getKey(), Constants.Code.SUCCESS.getMessage());

        this.data = data;
    }

    public ResponseFormat(int code, String message, Object data) {
        meta.put(Constants.Keys.CODE.getKey(), code);
        meta.put(Constants.Keys.MESSAGE.getKey(), message);
        this.data = data;
    }

    public ResponseFormat(int code, String message) {
        meta.put(Constants.Keys.CODE.getKey(), code);
        meta.put(Constants.Keys.MESSAGE.getKey(), message);
        this.data = new EmptyResultVo();
    }

    public ResponseFormat(ApiResponseCode status, int code, S message) {
    }

    public static ResponseFormat<String> createException(ApiResponseCode status, int code, String message) {
        return new ResponseFormat<>(status, code, message);
    }

    public static ResponseFormat<String> createException(String message) {
        return new ResponseFormat<String>(message);
    }

    public static ResponseFormat<String> createException(ApiResponseCode status, int code, String message, LogLevel level) {
        return new ResponseFormat<String>(status,code,message);
    }

    public void addDetail(String target, String message) {
        this.meta.computeIfAbsent(Constants.Keys.DETAIL.getKey(), k -> new ArrayList<Detail>());
        List<Detail> details = (List<Detail>) this.meta.get(Constants.Keys.DETAIL.getKey());
        details.add(new Detail(target, message));
    }

    public void setCode(int code) {
        meta.put(Constants.Keys.CODE.getKey(), code);
    }

    @JsonIgnore
    public int getCode() {
        return (int) meta.get(Constants.Keys.CODE.getKey());
    }

    public void setMessage(String message) {
        meta.put(Constants.Keys.MESSAGE.getKey(), message);
    }

    @JsonIgnore
    public String getMessage() {
        return (String) meta.get(Constants.Keys.MESSAGE.getKey());
    }

    @Getter
    @Setter
    public class Detail {
        private String target;
        private String message;

        Detail(String target, String message) {
            this.target = target;
            this.message = message;
        }
    }
}
