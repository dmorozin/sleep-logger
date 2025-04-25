package com.noom.interview.fullstack.sleep.service;

import com.noom.interview.fullstack.sleep.dto.request.SleepLogRequestDTO;
import com.noom.interview.fullstack.sleep.dto.response.AverageSleepLogsDTO;
import com.noom.interview.fullstack.sleep.dto.response.SleepLogDTO;

public interface SleepLogService {

    void createSleepLogForUser(SleepLogRequestDTO sleepLogRequestDTO, Integer userId);

    SleepLogDTO getSleepLogForUser(Integer userId, Integer sleepLogId);

    SleepLogDTO getSleepLogForUserForLastNight(Integer userId);

    AverageSleepLogsDTO getAverageSleepLogsForUser(Integer userId, Integer daysSince);
}
