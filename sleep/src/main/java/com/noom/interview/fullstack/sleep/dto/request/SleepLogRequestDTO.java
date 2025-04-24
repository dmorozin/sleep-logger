package com.noom.interview.fullstack.sleep.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.noom.interview.fullstack.sleep.utils.UserFeelEnum;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
public class SleepLogRequestDTO {

    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    @NonNull
    private UserFeelEnum userFeel;
}
