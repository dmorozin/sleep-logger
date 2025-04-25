package com.noom.interview.fullstack.sleep.dao.impl;

import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.model.SleepLogRowMapper;
import com.noom.interview.fullstack.sleep.utils.UserFeelEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    void testFindLastByUserId() {
        SleepLog mockSleepLog = sleepLog;
        when(jdbcTemplate.query(any(String.class), any(SleepLogRowMapper.class), any()))
                .thenReturn(java.util.List.of(mockSleepLog));

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
    void testFindFromLastNDaysByUserId() {
        SleepLog mockSleepLog = sleepLog;
        when(jdbcTemplate.query(any(String.class), any(SleepLogRowMapper.class), any(), any()))
                .thenReturn(java.util.List.of(mockSleepLog));

        List<SleepLog> result = sleepLogDAO.findFromLastNDaysByUserId(30, 1);
        assertNotNull(result);
        assertEquals(sleepLog.getUserId(), result.get(0).getUserId());
        assertEquals(sleepLog.getSleepDate(), result.get(0).getSleepDate());
    }
}
