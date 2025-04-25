package com.noom.interview.fullstack.sleep.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SleepLogDTO {

    private final String date;
    private final String totalTimeInBed;
    private final String timeInBedInterval;
    private final String userFeel;
}
