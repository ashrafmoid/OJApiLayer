package com.ashraf.ojapilayer.enums;

import lombok.Getter;

@Getter
public enum ContainerStatus {
    EXITED("exited"),
    RUNNING("running");

    private final String value;

    ContainerStatus(String value) {
        this.value = value;
    }
}
