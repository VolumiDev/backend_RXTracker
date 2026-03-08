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
public class AccessoryBlock extends Block {

    private String name;
    private AccessoryFormat format;
    private List<AccessoryExercise> exercises;
}
