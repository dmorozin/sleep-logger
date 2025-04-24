package com.noom.interview.fullstack.sleep.dao.impl;

import com.noom.interview.fullstack.sleep.dao.SleepLogDAO;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SleepLogDAOImpl implements SleepLogDAO {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insert(SleepLog sleepLog) throws DataAccessException {
        String sql = "INSERT INTO sleep_log (start_time, end_time, total_sleep_seconds, user_feel, user_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                sleepLog.getStartTime(),
                sleepLog.getEndTime(),
                sleepLog.getTotalSleepSeconds(),
                sleepLog.getUserFeel().name(),
                sleepLog.getUserId());
    }
}
