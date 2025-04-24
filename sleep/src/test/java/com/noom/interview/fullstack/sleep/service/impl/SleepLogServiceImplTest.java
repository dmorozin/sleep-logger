package com.noom.interview.fullstack.sleep.service.impl;

import com.noom.interview.fullstack.sleep.dao.SleepLogDAO;
import com.noom.interview.fullstack.sleep.dto.request.SleepLogRequestDTO;
import com.noom.interview.fullstack.sleep.dto.response.SleepLogResponseDTO;
import com.noom.interview.fullstack.sleep.model.SleepLog;
import com.noom.interview.fullstack.sleep.utils.SleepDateTimeUtils;
import com.noom.interview.fullstack.sleep.utils.UserFeelEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class SleepLogServiceImplTest {

    @InjectMocks
    private SleepLogServiceImpl sleepLogService;

    @Mock
    private SleepLogDAO sleepLogDAO;

    private SleepLogRequestDTO sleepLogRequestDTO;
    private SleepLog sleepLog;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        LocalTime startTime = LocalTime.of(22, 30);
        LocalTime endTime = LocalTime.of(6, 30);
        sleepLogRequestDTO = new SleepLogRequestDTO(startTime, endTime, UserFeelEnum.GOOD);
        sleepLog = new SleepLog();
        sleepLog.setSleepDate(LocalDate.now());
        sleepLog.setStartTime(startTime);
        sleepLog.setEndTime(endTime);
        sleepLog.setTotalSleepSeconds(28800L);
        sleepLog.setUserFeel(UserFeelEnum.GOOD);
        sleepLog.setUserId(1);
    }

    @Test
    void testCreateSleepLogForUser() {
        doNothing().when(sleepLogDAO).insert(any(SleepLog.class));
        sleepLogService.createSleepLogForUser(sleepLogRequestDTO, 1);
        verify(sleepLogDAO, times(1)).insert(any(SleepLog.class));
    }

    @Test
    void testGetSleepLogForUser() {
        when(sleepLogDAO.findByIdAndUserId(1, 1)).thenReturn(sleepLog);
        SleepLogResponseDTO response = sleepLogService.getSleepLogForUser(1, 1);
        assertSleepLogResponse(response);
    }

    @Test
    void testGetSleepLogForUserForLastNight() {
        when(sleepLogDAO.findLastByUserId(1)).thenReturn(sleepLog);
        SleepLogResponseDTO response = sleepLogService.getSleepLogForUserForLastNight(1);
        assertSleepLogResponse(response);
    }

    private void assertSleepLogResponse(SleepLogResponseDTO response) {
        assertNotNull(response);
        assertEquals(sleepLog.getUserFeel().getTitle(), response.getUserFeel());
        assertEquals(SleepDateTimeUtils.getFormattedTime(sleepLog.getTotalSleepSeconds()),
                response.getTotalTimeInBed());
    }

}
