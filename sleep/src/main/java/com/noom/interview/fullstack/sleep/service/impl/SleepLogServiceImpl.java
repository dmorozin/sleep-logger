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
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class SleepLogServiceImpl implements SleepLogService {

    private final SleepLogDAO sleepLogDAO;

    @Override
    public void createSleepLogForUser(SleepLogRequestDTO sleepLogRequestDTO, Integer userId) {
        LocalTime startTime = sleepLogRequestDTO.getStartTime();
        LocalTime endTime = sleepLogRequestDTO.getEndTime();
        Long totalSleepSeconds = Duration.between(endTime, startTime).toSeconds();

        SleepLog sleepLog = SleepLog.builder()
                .sleepDate(LocalDate.now())
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
        return getSleepLogResponseDTO(sleepLogDAO.findByIdAndUserId(sleepLogId, userId));
    }

    @Override
    public SleepLogResponseDTO getSleepLogForUserForLastNight(Integer userId) {
        return getSleepLogResponseDTO(sleepLogDAO.findLastByUserId(userId));
    }

    private static SleepLogResponseDTO getSleepLogResponseDTO(SleepLog sleepLog) {
        String sleepDate = SleepDateTimeUtils.getFormattedMonthDay(sleepLog.getSleepDate());
        String totalTimeInBed = SleepDateTimeUtils.getFormattedTime(sleepLog.getTotalSleepSeconds());
        String timeInBedInterval = SleepDateTimeUtils.getTimeInterval(sleepLog.getStartTime(), sleepLog.getEndTime());

        return SleepLogResponseDTO.builder()
                .date(sleepDate)
                .totalTimeInBed(totalTimeInBed)
                .timeInBedInterval(timeInBedInterval)
                .userFeel(sleepLog.getUserFeel().getTitle())
                .build();
    }
}
