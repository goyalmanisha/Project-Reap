package com.projectreap.ProjectReap.enums;

public enum Badge {
    gold(30),
    silver(20),
    bronze(10);

    private int weight;

    Badge( int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
