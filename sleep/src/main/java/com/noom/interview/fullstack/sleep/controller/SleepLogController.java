package com.noom.interview.fullstack.sleep.controller;

import com.noom.interview.fullstack.sleep.dto.request.SleepLogRequestDTO;
import com.noom.interview.fullstack.sleep.dto.response.AverageSleepLogsDTO;
import com.noom.interview.fullstack.sleep.dto.response.SleepLogDTO;
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

    @GetMapping("/{userId}/{sleepLogId}")
    public ResponseEntity<SleepLogDTO> getSingleSleepLog(@PathVariable Integer userId, @PathVariable Integer sleepLogId) {
        return ResponseEntity.ok(sleepLogService.getSleepLogForUser(userId, sleepLogId));
    }

    @GetMapping("/{userId}/last-night")
    public ResponseEntity<SleepLogDTO> getSleepLogForLastNight(@PathVariable Integer userId) {
        return ResponseEntity.ok(sleepLogService.getSleepLogForUserForLastNight(userId));
    }

    @GetMapping("/{userId}/average")
    public ResponseEntity<AverageSleepLogsDTO> getAverageSleepLogs(@PathVariable Integer userId) {
        return ResponseEntity.ok(sleepLogService.getAverageSleepLogsForUser(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> createSleepLog(@RequestBody SleepLogRequestDTO sleepLogRequestDTO,
                                               @PathVariable Integer userId) throws DataAccessException {
        sleepLogService.createSleepLogForUser(sleepLogRequestDTO, userId);
        return ResponseEntity.ok().build();
    }
}
