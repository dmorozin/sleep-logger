package com.noom.interview.fullstack.sleep.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.noom.interview.fullstack.sleep.utils.UserFeelEnum;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalTime;

@Data
public class SleepLogRequestDTO {

    @NonNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NonNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @NonNull
    private UserFeelEnum userFeel;
}
