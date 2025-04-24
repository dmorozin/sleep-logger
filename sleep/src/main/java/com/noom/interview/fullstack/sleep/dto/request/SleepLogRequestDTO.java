package com.noom.interview.fullstack.sleep.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.noom.interview.fullstack.sleep.utils.UserFeelEnum;
import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
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

    // added for testing purposes
    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate sleepDate;
}
