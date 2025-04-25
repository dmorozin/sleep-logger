package com.noom.interview.fullstack.sleep.dao.impl;

import com.noom.interview.fullstack.sleep.dao.SleepLogDAO;
import com.noom.interview.fullstack.sleep.exception.DatabaseFetchException;
import com.noom.interview.fullstack.sleep.exception.ResourceNotFoundException;
import com.noom.interview.fullstack.sleep.exception.SleepLogInsertException;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.model.SleepLogRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.noom.interview.fullstack.sleep.utils.Constants.*;

@Repository
@RequiredArgsConstructor
@Log4j2
public class SleepLogDAOImpl implements SleepLogDAO {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insert(SleepLog sleepLog) {
        String sql = "INSERT INTO sleep_log (sleep_date, start_time, end_time, total_sleep_seconds, user_feel, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    sleepLog.getSleepDate(),
                    sleepLog.getStartTime(),
                    sleepLog.getEndTime(),
                    sleepLog.getTotalSleepSeconds(),
                    sleepLog.getUserFeel().name(),
                    sleepLog.getUserId());
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new SleepLogInsertException(String.format(SLEEP_LOG_INSERT_ERROR, sleepLog.getSleepDate().toString(), sleepLog.getUserId()));
        }
    }

    @Override
    public SleepLog findByIdAndUserId(Integer sleepLogId, Integer userId) {
        String sql = "SELECT * FROM sleep_log WHERE id = ? AND user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new SleepLogRowMapper(), sleepLogId, userId);
        } catch (EmptyResultDataAccessException e) {
            log.warn(e.getMessage());
            throw new ResourceNotFoundException(String.format(SLEEP_LOG_NOT_FOUND, sleepLogId, userId));
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseFetchException(String.format(DATABASE_FETCH_ERROR, userId));
        }
    }

    @Override
    public SleepLog findLastByUserId(Integer userId) {
        String sql = "SELECT * FROM sleep_log WHERE sleep_date::date = current_date AND user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new SleepLogRowMapper(), userId);
        } catch (EmptyResultDataAccessException e) {
            log.warn(e.getMessage());
            throw new ResourceNotFoundException(String.format(LAST_NIGHT_SLEEP_LOG_NOT_FOUND, userId));
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseFetchException(String.format(DATABASE_FETCH_ERROR, userId));
        }
    }

    @Override
    public List<SleepLog> findFromLastNDaysByUserId(Integer daysSince, Integer userId) {
        LocalDate dateSince = LocalDate.now().minusDays(daysSince);
        String sql = "SELECT * FROM sleep_log WHERE sleep_date >= ? AND user_id = ? ORDER BY sleep_date";
        try {
            return jdbcTemplate.query(sql, new SleepLogRowMapper(), dateSince, userId);
        } catch (DataAccessException e) {
            log.error(e.getMessage(), e);
            throw new DatabaseFetchException(String.format(DATABASE_FETCH_ERROR, userId));
        }
    }
}
