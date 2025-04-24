package com.noom.interview.fullstack.sleep.dao;

import com.noom.interview.fullstack.sleep.model.SleepLog;
import org.springframework.dao.DataAccessException;

public interface SleepLogDAO {

    void insert(SleepLog sleepLog) throws DataAccessException;

    SleepLog findByIdAndUserId(Integer sleepLogId, Integer userId);

//    SleepLog findLastByUserId(Integer userId);
}
