package com.diego.iatrainig.domain.bloque;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WodAmrapBlock extends Block {

    private String wodName;

    @JsonProperty("is_benchmark")
    private boolean isBenchmark;

    private int durationMin;
    private List<StrengthExercise> exercises;
    private String strategy;
}
