package com.ashraf.ojapilayer.enums;

import lombok.Getter;

@Getter
public enum Topic {
    ADHOC("Adhoc"),
    GREEDY("Greedy"),
    DP("DP"),
    STRING("String");
    private final String value;
    Topic(String value) {
        this.value = value;
    }
}
