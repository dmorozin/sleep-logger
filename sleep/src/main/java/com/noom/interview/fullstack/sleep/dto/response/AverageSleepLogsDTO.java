package com.noom.interview.fullstack.sleep.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class AverageSleepLogsDTO {

    private final String dateRange;
    private final String averageTotalTimeInBed;
    private final String averageTimeInBedInterval;
    private final Map<String, Integer> userFeels;
}
