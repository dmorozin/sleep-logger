package com.noom.interview.fullstack.sleep.dao;

import com.noom.interview.fullstack.sleep.model.SleepLog;

import java.util.List;

/**
 * Data Access Object (DAO) interface for managing sleep log records in the database.
 */
public interface SleepLogDAO {

    /**
     * Inserts a new sleep log record into the database.
     *
     * @param sleepLog the sleep log to be inserted
     */
    void insert(SleepLog sleepLog);

    /**
     * Fetch a sleep log by its ID and associated user ID.
     *
     * @param sleepLogId the ID of the sleep log
     * @param userId     the ID of the user
     * @return the found sleep log
     */
    SleepLog findByIdAndUserId(Integer sleepLogId, Integer userId);

    /**
     * Fetch the sleep log for today's date for a given user.
     * Can throw ResourceNotFoundException if there is no record for today's date
     *
     * @param userId the ID of the user
     * @return the most recent sleep log
     */
    SleepLog findTodayLogByUserId(Integer userId);

    /**
     * Fetch all sleep logs for a given user starting from a number of days in the past.
     *
     * @param daysSince the number of days to look back
     * @param userId    the ID of the user
     * @return list of sleep logs within the specified period
     */
    List<SleepLog> findFromLastNDaysByUserId(Integer daysSince, Integer userId);
}
