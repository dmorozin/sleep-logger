package com.noom.interview.fullstack.sleep.controller;

import com.noom.interview.fullstack.sleep.dto.request.SleepLogRequestDTO;
import com.noom.interview.fullstack.sleep.dto.response.SleepLogResponseDTO;
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
    public ResponseEntity<SleepLogResponseDTO> getSingleSleepLog(@PathVariable Integer userId, @PathVariable Integer sleepLogId) {
        return ResponseEntity.ok(sleepLogService.getSleepLogForUser(userId, sleepLogId));
    }

    @GetMapping("/{userId}/last-night")
    public ResponseEntity<SleepLogResponseDTO> getSleepLogForLastNight(@PathVariable Integer userId) {
        return ResponseEntity.ok(sleepLogService.getSleepLogForUserForLastNight(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> createSleepLog(@RequestBody SleepLogRequestDTO sleepLogRequestDTO,
                                               @PathVariable Integer userId) throws DataAccessException {
        sleepLogService.createSleepLogForUser(sleepLogRequestDTO, userId);
        return ResponseEntity.ok().build();
    }
}
