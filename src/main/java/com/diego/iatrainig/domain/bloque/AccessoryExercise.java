package com.diego.iatrainig.domain.bloque;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessoryExercise {

    private String name;
    private int sets;
    private int reps;
    private Integer repsMin;
    private Integer repsMax;
    private String implement;
    private String weightReference;
    private String notes;
}
