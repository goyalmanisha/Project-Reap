package com.projectreap.ProjectReap.enums;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMIN("admin"),
    USER("user"),
    SUPERVISOR("supervisor"),
    PRACTICE_HEAD("Practice head");

   // List<String> values;
    String value;

    Role(String value) {
        this.value=value;
        //this.values = Arrays.asList(value);
    }

    public String getValue() {
        return value;
    }
}

