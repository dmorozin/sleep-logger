package com.noom.interview.fullstack.sleep.controller;

import com.noom.interview.fullstack.sleep.dto.request.SleepLogRequestDTO;
import com.noom.interview.fullstack.sleep.service.SleepLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/sleep-log")
@RequiredArgsConstructor
public class SleepLogController {

    private final SleepLogService sleepLogService;

    @PostMapping
    public ResponseEntity<Void> createSleepLog(@RequestBody SleepLogRequestDTO sleepLogRequestDTO,
                                               @RequestParam(value = "userId") Integer userId) throws DataAccessException {
        sleepLogService.createSleepLog(sleepLogRequestDTO, userId);
        return ResponseEntity.ok().build();
    }
}
