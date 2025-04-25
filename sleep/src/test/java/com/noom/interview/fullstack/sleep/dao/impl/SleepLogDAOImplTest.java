package com.noom.interview.fullstack.sleep.dao.impl;

import com.noom.interview.fullstack.sleep.exception.DatabaseFetchException;
import com.noom.interview.fullstack.sleep.exception.ResourceNotFoundException;
import com.noom.interview.fullstack.sleep.exception.SleepLogInsertException;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.model.SleepLogRowMapper;
import com.noom.interview.fullstack.sleep.utils.UserFeelEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.noom.interview.fullstack.sleep.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SleepLogDAOImplTest {

    @InjectMocks
    private SleepLogDAOImpl sleepLogDAO;

    @Mock
    private JdbcTemplate jdbcTemplate;

    private SleepLog sleepLog;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sleepLog = new SleepLog();
        sleepLog.setSleepDate(LocalDate.now());
        sleepLog.setStartTime(LocalTime.of(22, 0));
        sleepLog.setEndTime(LocalTime.of(6, 0));
        sleepLog.setTotalSleepSeconds(28800L);
        sleepLog.setUserFeel(UserFeelEnum.GOOD);
        sleepLog.setUserId(1);
    }

    @Test
    void testInsert() {
        when(jdbcTemplate.update(any(String.class), any(), any(), any(), any(), any(), any()))
                .thenReturn(1);
        sleepLogDAO.insert(sleepLog);
        verify(jdbcTemplate, times(1))
                .update(any(String.class), any(), any(), any(), any(), any(), any());
    }

    @Test
    void testInsert_shouldThrowSleepLogInsertException_whenDataAccessExceptionIsThrown() {
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any(), any())).thenThrow(new DataAccessException("Database insert failed") {});

        SleepLogInsertException exception = assertThrows(SleepLogInsertException.class, () -> sleepLogDAO.insert(sleepLog));

        assertEquals(String.format(SLEEP_LOG_INSERT_ERROR, sleepLog.getSleepDate().toString(), sleepLog.getUserId()), exception.getMessage());
    }


    @Test
    void testFindByIdAndUserId() {
        SleepLog mockSleepLog = sleepLog;
        when(jdbcTemplate.queryForObject(any(String.class), any(SleepLogRowMapper.class), any(), any()))
                .thenReturn(mockSleepLog);

        SleepLog result = sleepLogDAO.findByIdAndUserId(1, 1);
        assertNotNull(result);
        assertEquals(sleepLog.getUserId(), result.getUserId());
        assertEquals(sleepLog.getSleepDate(), result.getSleepDate());
    }

    @Test
    void testFindByIdAndUserId_shouldThrowResourceNotFoundException_whenEmptyResultDataAccessExceptionIsThrown() {
        when(jdbcTemplate.queryForObject(any(String.class), any(SleepLogRowMapper.class), any(), any())).thenThrow(new EmptyResultDataAccessException(1));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> sleepLogDAO.findByIdAndUserId(1, 1));

        assertEquals(String.format(SLEEP_LOG_NOT_FOUND, 1, 1), exception.getMessage());
    }

    @Test
    void testFindByIdAndUserId_shouldThrowDatabaseFetchException_whenDataAccessExceptionIsThrown() {
        when(jdbcTemplate.queryForObject(any(String.class), any(SleepLogRowMapper.class), any(), any())).thenThrow(new DataAccessException("Database fetch failed") {});

        DatabaseFetchException exception = assertThrows(DatabaseFetchException.class, () -> sleepLogDAO.findByIdAndUserId(1, 1));

        assertEquals(String.format(DATABASE_FETCH_ERROR, 1), exception.getMessage());
    }

    @Test
    void testFindLastByUserId() {
        SleepLog mockSleepLog = sleepLog;
        when(jdbcTemplate.queryForObject(any(String.class), any(SleepLogRowMapper.class), any()))
                .thenReturn(mockSleepLog);

        SleepLog result = sleepLogDAO.findLastByUserId(1);
        assertNotNull(result);
        assertEquals(sleepLog.getUserId(), result.getUserId());
        assertEquals(sleepLog.getSleepDate(), result.getSleepDate());
    }

    @Test
    void testFindLastByUserId_NoResults() {
        when(jdbcTemplate.query(any(String.class), any(SleepLogRowMapper.class), any()))
                .thenReturn(java.util.Collections.emptyList());

        SleepLog result = sleepLogDAO.findLastByUserId(1);
        assertNull(result);
    }


    @Test
    void testFindLastByUserId_shouldThrowResourceNotFoundException_whenEmptyResultDataAccessExceptionIsThrown() {
        when(jdbcTemplate.queryForObject(any(String.class), any(SleepLogRowMapper.class), any()))
                .thenThrow(new EmptyResultDataAccessException(1));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> sleepLogDAO.findLastByUserId(1));

        assertEquals(String.format(LAST_NIGHT_SLEEP_LOG_NOT_FOUND, 1), exception.getMessage());
    }

    @Test
    void testFindLastByUserId_shouldThrowDatabaseFetchException_whenDataAccessExceptionIsThrown() {
        when(jdbcTemplate.queryForObject(any(String.class), any(SleepLogRowMapper.class), any()))
                .thenThrow(new DataAccessException("Database fetch failed") {});

        DatabaseFetchException exception = assertThrows(DatabaseFetchException.class, () -> sleepLogDAO.findLastByUserId(1));

        assertEquals(String.format(DATABASE_FETCH_ERROR, 1), exception.getMessage());
    }

    @Test
    void testFindFromLastNDaysByUserId() {
        SleepLog mockSleepLog = sleepLog;
        when(jdbcTemplate.query(any(String.class), any(SleepLogRowMapper.class), any(), any()))
                .thenReturn(java.util.List.of(mockSleepLog));

        List<SleepLog> result = sleepLogDAO.findFromLastNDaysByUserId(30, 1);
        assertNotNull(result);
        assertEquals(sleepLog.getUserId(), result.get(0).getUserId());
        assertEquals(sleepLog.getSleepDate(), result.get(0).getSleepDate());
    }

    @Test
    void testFindFromLastNDaysByUserId_shouldThrowDatabaseFetchException_whenDataAccessExceptionIsThrown() {
        when(jdbcTemplate.query(any(String.class), any(SleepLogRowMapper.class), any(), any()))
                .thenThrow(new DataAccessException("Database fetch failed") {});

        DatabaseFetchException exception = assertThrows(DatabaseFetchException.class, () -> sleepLogDAO.findFromLastNDaysByUserId(7, 1));

        assertEquals(String.format(DATABASE_FETCH_ERROR, 1), exception.getMessage());
    }
}
