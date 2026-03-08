package com.diego.iatrainig.domain.bloque;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Intensity {

    private String type;
    private Integer percentageRmMin;
    private Integer percentageRmMax;
    private Double weightKg;
}
