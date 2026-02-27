package com.diego.iatrainig.domain;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PersonalRecord {
    private String exerciseId;
    private String exerciseName;
    private double oneRepMax;
    private LocalDate achievedAt;
}
