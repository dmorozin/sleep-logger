package com.noom.interview.fullstack.sleep.model;

import com.noom.interview.fullstack.sleep.utils.UserFeelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SleepLog {

    private Integer id;
    private LocalDate sleepDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long totalSleepSeconds;
    private UserFeelEnum userFeel;
    private Integer userId;
}
