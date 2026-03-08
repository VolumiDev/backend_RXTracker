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
public class WodForTimeBlock extends Block {

    private String wodName;

    @JsonProperty("is_benchmark")
    private boolean isBenchmark;

    private Integer timeCapMin;
    private WodForTimeFormat format;
    private List<StrengthExercise> exercises;
    private WodScaling scaling;
    private String strategy;
}
