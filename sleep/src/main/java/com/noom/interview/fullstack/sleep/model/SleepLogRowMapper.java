package com.noom.interview.fullstack.sleep.model;

import com.noom.interview.fullstack.sleep.utils.UserFeelEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SleepLogRowMapper implements RowMapper<SleepLog> {

    public SleepLog mapRow(ResultSet resultSet, int i) throws SQLException {

        SleepLog sleepLog = new SleepLog();
        sleepLog.setId(resultSet.getInt("id"));
        sleepLog.setSleepDate(resultSet.getDate("sleep_date").toLocalDate());
        sleepLog.setStartTime(resultSet.getTime("start_time").toLocalTime());
        sleepLog.setEndTime(resultSet.getTime("end_time").toLocalTime());
        sleepLog.setTotalSleepSeconds(resultSet.getLong("total_sleep_seconds"));
        sleepLog.setUserFeel(UserFeelEnum.valueOf(resultSet.getString("user_feel")));
        sleepLog.setUserId(resultSet.getInt("user_id"));

        return sleepLog;
    }
}