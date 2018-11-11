package com.projectreap.ProjectReap.enums;

public enum Badge {
    Gold(30),
    Silver(20),
    Bronze(10);

    private final int value;

    Badge(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}
