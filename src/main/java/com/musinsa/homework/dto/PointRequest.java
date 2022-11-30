package com.musinsa.homework.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PointRequest {

    private Long userSeq;
    @JsonFormat(pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private LocalDate regDate;

    public LocalDate getRegDate() {
        if (Objects.isNull(this.regDate)) {
            return LocalDate.now();
        }
        return this.regDate;
    }
}
