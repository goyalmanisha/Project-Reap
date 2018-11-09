package com.projectreap.ProjectReap.enums;

public enum Role {
    ADMIN("admin"),
    USER("user"),
    SUPERVISOR("Supervisor"),
    PRACTICE_HEAD("practice head");

    String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
