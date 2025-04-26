package com.noom.interview.fullstack.sleep.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.noom.interview.fullstack.sleep.utils.UserFeelEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class SleepLogRequestDTO {

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @NotNull
    private UserFeelEnum userFeel;

    // added for testing purposes
    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate sleepDate;
}
