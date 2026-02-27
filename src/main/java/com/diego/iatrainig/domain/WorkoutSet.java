package com.diego.iatrainig.domain;

import lombok.Data;

@Data
public class WorkoutSet {
    private String exerciseId;
    private int order;
    private int targetReps;
    private double targetWeight;
    private int actualReps;
    private double actualWeight;
    private int rpe;
}
