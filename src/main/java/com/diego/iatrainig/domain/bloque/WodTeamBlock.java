package com.diego.iatrainig.domain.bloque;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WodTeamBlock extends Block {

    private TeamFormat format;
    private List<StrengthExercise> exercises;
    private int durationMin;
}
