package com.noom.interview.fullstack.sleep.dao;

import com.noom.interview.fullstack.sleep.model.SleepLog;

import java.util.List;

public interface SleepLogDAO {

    void insert(SleepLog sleepLog);

    SleepLog findByIdAndUserId(Integer sleepLogId, Integer userId);

    SleepLog findLastByUserId(Integer userId);

    List<SleepLog> findFromLastNDaysByUserId(Integer daysSince, Integer userId);
}
