package com.noom.interview.fullstack.sleep.service.impl;

import com.noom.interview.fullstack.sleep.dao.SleepLogDAO;
import com.noom.interview.fullstack.sleep.dto.request.SleepLogRequestDTO;
import com.noom.interview.fullstack.sleep.dto.response.AverageSleepLogsDTO;
import com.noom.interview.fullstack.sleep.dto.response.SleepLogDTO;
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
import java.util.Collections;
import java.util.List;

import static com.noom.interview.fullstack.sleep.utils.Constants.NO_DATA;
import static org.junit.jupiter.api.Assertions.*;
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
        sleepLogRequestDTO = new SleepLogRequestDTO(startTime, endTime, UserFeelEnum.GOOD, null);
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
        SleepLogDTO response = sleepLogService.getSleepLogForUser(1, 1);
        assertSleepLogResponse(response);
    }

    @Test
    void testGetSleepLogForUserForLastNight() {
        when(sleepLogDAO.findTodayLogByUserId(1)).thenReturn(sleepLog);
        SleepLogDTO response = sleepLogService.getSleepLogForUserForLastNight(1);
        assertSleepLogResponse(response);
    }

    private void assertSleepLogResponse(SleepLogDTO response) {
        assertNotNull(response);
        assertEquals(sleepLog.getUserFeel().getTitle(), response.getUserFeel());
        assertEquals(SleepDateTimeUtils.getFormattedTime(sleepLog.getTotalSleepSeconds()),
                response.getTotalTimeInBed());
    }

    @Test
    void testGetAverageSleepLogsForUser() {
        List<SleepLog> mockLogs = List.of(
                buildLog(22, 0, 6, 0, 28800, UserFeelEnum.GOOD),
                buildLog(23, 0, 7, 0, 28800, UserFeelEnum.OK),
                buildLog(21, 30, 5, 30, 28800, UserFeelEnum.BAD)
        );

        when(sleepLogDAO.findFromLastNDaysByUserId(3, 1)).thenReturn(mockLogs);

        AverageSleepLogsDTO result = sleepLogService.getAverageSleepLogsForUser(1, 3);
        assertNotNull(result);
        assertEquals("8 h 0 min", result.getAverageTotalTimeInBed());
        assertEquals(3, result.getUserFeels().values().stream().mapToInt(i -> i).sum());
        assertTrue(result.getDateRange().contains("to"));
    }

    @Test
    void testGetAverageSleepLogsForUser_ShouldReturnEmptyList_WhenNoSleepLogs() {
        List<SleepLog> mockLogs = Collections.emptyList();

        when(sleepLogDAO.findFromLastNDaysByUserId(3, 1)).thenReturn(mockLogs);

        AverageSleepLogsDTO result = sleepLogService.getAverageSleepLogsForUser(1, 3);
        assertNotNull(result);
        assertEquals(NO_DATA, result.getAverageTimeInBedInterval());
        assertEquals(NO_DATA, result.getAverageTotalTimeInBed());
        assertEquals(NO_DATA, result.getDateRange());
        assertEquals(Collections.emptyMap(), result.getUserFeels());
    }

    private SleepLog buildLog(int startHour, int startMin, int endHour, int endMin, long totalSeconds, UserFeelEnum feel) {
        return SleepLog.builder()
                .sleepDate(LocalDate.now())
                .startTime(LocalTime.of(startHour, startMin))
                .endTime(LocalTime.of(endHour, endMin))
                .totalSleepSeconds(totalSeconds)
                .userFeel(feel)
                .userId(1)
                .build();
    }

}
