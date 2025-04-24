package com.noom.interview.fullstack.sleep.model;

import com.noom.interview.fullstack.sleep.utils.UserFeelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SleepLog {

    private Integer id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long totalSleepSeconds;
    private UserFeelEnum userFeel;
    private Integer userId;
}
