package com.noom.interview.fullstack.sleep.service.impl;

import com.noom.interview.fullstack.sleep.dao.SleepLogDAO;
import com.noom.interview.fullstack.sleep.dto.request.SleepLogRequestDTO;
import com.noom.interview.fullstack.sleep.dto.response.AverageSleepLogsDTO;
import com.noom.interview.fullstack.sleep.dto.response.SleepLogDTO;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.service.SleepLogService;
import com.noom.interview.fullstack.sleep.utils.SleepDateTimeUtils;
import com.noom.interview.fullstack.sleep.utils.UserFeelEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.noom.interview.fullstack.sleep.utils.Constants.NO_DATA;

@Service
@RequiredArgsConstructor
public class SleepLogServiceImpl implements SleepLogService {

    private final SleepLogDAO sleepLogDAO;

    @Override
    public void createSleepLogForUser(SleepLogRequestDTO dto, Integer userId) {
        LocalTime startTime = dto.getStartTime();
        LocalTime endTime = dto.getEndTime();
        LocalDate sleepDate = dto.getSleepDate() != null ? dto.getSleepDate() : LocalDate.now();

        LocalDateTime startDateTime = LocalDateTime.of(sleepDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(sleepDate, endTime);
        if (startTime.isAfter(endTime)) {
            startDateTime = startDateTime.minusDays(1);
        }

        long totalSleepSeconds = Duration.between(startDateTime, endDateTime).toSeconds();

        SleepLog sleepLog = SleepLog.builder()
                .sleepDate(sleepDate)
                .startTime(startTime)
                .endTime(endTime)
                .totalSleepSeconds(totalSleepSeconds)
                .userFeel(dto.getUserFeel())
                .userId(userId)
                .build();

        sleepLogDAO.insert(sleepLog);
    }

    @Override
    public SleepLogDTO getSleepLogForUser(Integer userId, Integer sleepLogId) {
        return convertToSleepLogDTO(sleepLogDAO.findByIdAndUserId(sleepLogId, userId));
    }

    @Override
    public SleepLogDTO getSleepLogForUserForLastNight(Integer userId) {
        return convertToSleepLogDTO(sleepLogDAO.findLastByUserId(userId));
    }

    @Override
    public AverageSleepLogsDTO getAverageSleepLogsForUser(Integer userId, Integer daysSince) {
        List<SleepLog> logs = sleepLogDAO.findFromLastNDaysByUserId(daysSince, userId);

        if (logs.isEmpty()) {
            return AverageSleepLogsDTO.builder()
                    .averageTimeInBedInterval(NO_DATA)
                    .averageTotalTimeInBed(NO_DATA)
                    .dateRange(NO_DATA)
                    .userFeels(Collections.emptyMap())
                    .build();
        }

        long totalSeconds = 0, startTimeNano = 0, endTimeNano = 0;

        Map<String, Integer> userFeelMap = new HashMap<>();
        for (UserFeelEnum feel : UserFeelEnum.values()) {
            userFeelMap.put(feel.getTitle(), 0);
        }

        for (SleepLog log : logs) {
            totalSeconds += log.getTotalSleepSeconds();
            startTimeNano += log.getStartTime().toNanoOfDay();
            endTimeNano += log.getEndTime().toNanoOfDay();

            String userFeel = log.getUserFeel().getTitle();
            userFeelMap.put(userFeel, userFeelMap.get(userFeel) + 1);
        }

        int logSize = logs.size();
        long averageSleepSeconds = totalSeconds / logSize;
        long averageStartTime = startTimeNano / logSize;
        long averageEndTime = endTimeNano / logSize;

        String averageTime = SleepDateTimeUtils.getFormattedTime(averageSleepSeconds);
        String averageInterval = SleepDateTimeUtils.getTimeInterval(LocalTime.ofNanoOfDay(averageStartTime),
                LocalTime.ofNanoOfDay(averageEndTime));

        String pattern = "MMM";
        String dateRange = SleepDateTimeUtils.getFormattedMonthDay(logs.get(0).getSleepDate(), pattern) + " to " +
                SleepDateTimeUtils.getFormattedMonthDay(logs.get(logSize - 1).getSleepDate(), pattern);

        return AverageSleepLogsDTO.builder()
                .averageTimeInBedInterval(averageInterval)
                .averageTotalTimeInBed(averageTime)
                .dateRange(dateRange)
                .userFeels(userFeelMap)
                .build();
    }

    private static SleepLogDTO convertToSleepLogDTO(SleepLog sleepLog) {
        String sleepDate = SleepDateTimeUtils.getFormattedMonthDay(sleepLog.getSleepDate(), "MMMM");
        String totalTimeInBed = SleepDateTimeUtils.getFormattedTime(sleepLog.getTotalSleepSeconds());
        String timeInBedInterval = SleepDateTimeUtils.getTimeInterval(sleepLog.getStartTime(), sleepLog.getEndTime());

        return SleepLogDTO.builder()
                .sleepLogId(sleepLog.getId())
                .date(sleepDate)
                .totalTimeInBed(totalTimeInBed)
                .timeInBedInterval(timeInBedInterval)
                .userFeel(sleepLog.getUserFeel().getTitle())
                .build();
    }
}
