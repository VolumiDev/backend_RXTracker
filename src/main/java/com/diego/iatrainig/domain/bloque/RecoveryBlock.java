package com.diego.iatrainig.domain.bloque;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RecoveryBlock extends Block {

    private String modality;
    private String description;
    private Integer distanceMin;
    private Integer distanceMax;
    private Integer durationMin;
}
