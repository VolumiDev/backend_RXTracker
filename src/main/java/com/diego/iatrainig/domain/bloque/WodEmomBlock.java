package com.diego.iatrainig.domain.bloque;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WodEmomBlock extends Block {

    private int totalDurationMin;
    private EmomFormat format;
}
