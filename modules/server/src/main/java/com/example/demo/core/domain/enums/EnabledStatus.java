package com.example.demo.core.domain.enums;

public enum EnabledStatus {
    DISABLED(0),
    ENABLED(1);

    private final int value;

    EnabledStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static EnabledStatus fromValue(int value) {
        for (EnabledStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
} 