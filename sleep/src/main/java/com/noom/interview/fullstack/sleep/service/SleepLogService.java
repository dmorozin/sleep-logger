package com.noom.interview.fullstack.sleep.service;

import com.noom.interview.fullstack.sleep.dto.request.SleepLogRequestDTO;
import com.noom.interview.fullstack.sleep.dto.response.AverageSleepLogsDTO;
import com.noom.interview.fullstack.sleep.dto.response.SleepLogDTO;

/**
 * Service for creating user sleep log and fetching logs with different statistics
 */
public interface SleepLogService {

    /**
     * Creates a new sleep log for a specific user.
     *
     * @param sleepLogRequestDTO the sleep log data
     * @param userId             the ID of the user
     */
    void createSleepLogForUser(SleepLogRequestDTO sleepLogRequestDTO, Integer userId);

    /**
     * Retrieves a specific sleep log for a given user.
     *
     * @param userId     the ID of the user
     * @param sleepLogId the ID of the sleep log
     * @return the sleep log DTO
     */
    SleepLogDTO getSleepLogForUser(Integer userId, Integer sleepLogId);

    /**
     * Retrieves the most recent sleep log (last night) for a given user.
     *
     * @param userId the ID of the user
     * @return the sleep log DTO for last night
     */
    SleepLogDTO getSleepLogForUserForLastNight(Integer userId);

    /**
     * Calculates and returns the average sleep statistics for a user over a given number of days.
     *
     * @param userId    the ID of the user
     * @param daysSince the number of days to calculate the average from
     * @return the average sleep log data
     */
    AverageSleepLogsDTO getAverageSleepLogsForUser(Integer userId, Integer daysSince);
}
