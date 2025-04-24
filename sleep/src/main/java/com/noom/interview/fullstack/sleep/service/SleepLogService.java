package com.noom.interview.fullstack.sleep.service;

import com.noom.interview.fullstack.sleep.dto.request.SleepLogRequestDTO;
import com.noom.interview.fullstack.sleep.dto.response.SleepLogResponseDTO;

public interface SleepLogService {

    void createSleepLogForUser(SleepLogRequestDTO sleepLogRequestDTO, Integer userId);

    SleepLogResponseDTO getSleepLogForUser(Integer userId, Integer sleepLogId);

    SleepLogResponseDTO getSleepLogForUserForLastNight(Integer userId);
}
