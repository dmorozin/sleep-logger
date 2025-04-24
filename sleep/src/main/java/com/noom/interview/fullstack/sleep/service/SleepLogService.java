package com.noom.interview.fullstack.sleep.service;

import com.noom.interview.fullstack.sleep.dto.request.SleepLogRequestDTO;

public interface SleepLogService {

    void createSleepLog(SleepLogRequestDTO sleepLogRequestDTO, Integer userId);
}
