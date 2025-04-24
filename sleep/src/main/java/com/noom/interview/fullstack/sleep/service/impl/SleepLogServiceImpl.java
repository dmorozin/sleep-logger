package com.noom.interview.fullstack.sleep.service.impl;

import com.noom.interview.fullstack.sleep.dao.SleepLogDAO;
import com.noom.interview.fullstack.sleep.dto.request.SleepLogRequestDTO;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.service.SleepLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SleepLogServiceImpl implements SleepLogService {

    private final SleepLogDAO sleepLogDAO;

    @Override
    public void createSleepLog(SleepLogRequestDTO sleepLogRequestDTO, Integer userId) {
        LocalDateTime startTime = sleepLogRequestDTO.getStartTime();
        LocalDateTime endTime = sleepLogRequestDTO.getEndTime();
        Long totalSleepSeconds = Duration.between(startTime, endTime).toSeconds();

        SleepLog sleepLog = SleepLog.builder()
                .startTime(startTime)
                .endTime(endTime)
                .totalSleepSeconds(totalSleepSeconds)
                .userFeel(sleepLogRequestDTO.getUserFeel())
                .userId(userId)
                .build();

        sleepLogDAO.insert(sleepLog);
    }
}
