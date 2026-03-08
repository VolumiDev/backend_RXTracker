package com.diego.iatrainig.domain.bloque;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrengthExercise {

    private String name;
    private String category;
    private int sets;
    private int reps;
    private Intensity intensity;
    private Tempo tempo;
    private Integer restBetweenSetsSec;
    private String notes;
}
