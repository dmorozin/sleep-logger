package com.noom.interview.fullstack.sleep.service.impl;

import com.noom.interview.fullstack.sleep.dao.SleepLogDAO;
import com.noom.interview.fullstack.sleep.dto.request.SleepLogRequestDTO;
import com.noom.interview.fullstack.sleep.dto.response.SleepLogResponseDTO;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.service.SleepLogService;
import com.noom.interview.fullstack.sleep.utils.SleepDateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SleepLogServiceImpl implements SleepLogService {

    private final SleepLogDAO sleepLogDAO;

    @Override
    public void createSleepLogForUser(SleepLogRequestDTO sleepLogRequestDTO, Integer userId) {
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

    @Override
    public SleepLogResponseDTO getSleepLogForUser(Integer userId, Integer sleepLogId) {
        SleepLog sleepLog = sleepLogDAO.findByIdAndUserId(sleepLogId, userId);

        String date = SleepDateTimeUtils.getFormattedMonthDay(sleepLog.getStartTime());
        String totalTimeInBed = SleepDateTimeUtils.getFormattedTime(sleepLog.getTotalSleepSeconds());
        String timeInBedInterval = SleepDateTimeUtils.getTimeInterval(sleepLog.getStartTime(), sleepLog.getEndTime());

        return SleepLogResponseDTO.builder()
                .date(date)
                .totalTimeInBed(totalTimeInBed)
                .timeInBedInterval(timeInBedInterval)
                .userFeel(sleepLog.getUserFeel().getTitle())
                .build();
    }

//    @Override
//    public SleepLogResponseDTO getSleepLogForUserForLastNight(Integer userId) {
//
//
//    }

}
