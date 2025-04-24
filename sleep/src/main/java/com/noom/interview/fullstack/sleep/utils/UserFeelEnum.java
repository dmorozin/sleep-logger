package com.noom.interview.fullstack.sleep.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserFeelEnum {
    BAD("Bad"),
    OK("OK"),
    GOOD("Good");

    private final String title;
}
