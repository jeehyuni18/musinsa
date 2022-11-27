package com.musinsa.homework.controller;

import com.musinsa.homework.dto.PointRequest;
import com.musinsa.homework.global.ResponseFormat;
import com.musinsa.homework.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/point")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PointController {
    private final PointService pointService;


    @GetMapping("/{pointSeq}")
    public ResponseFormat pointHistoryDetail(@PathVariable Long pointSeq) {
        return new ResponseFormat(pointService.findPointHistory(pointSeq));
    }
    @GetMapping("/list/{date}")
    public ResponseFormat pointHistoryList(@PathVariable String date) {
        return new ResponseFormat(pointService.findPointHistoryAll(date));
    }

    @PostMapping("/add")
    public ResponseFormat addPoint(@RequestBody PointRequest pointRequest) {
        pointService.addPoint(pointRequest);
        return new ResponseFormat();
    }


}
