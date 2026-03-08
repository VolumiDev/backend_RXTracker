package com.diego.iatrainig.domain.bloque;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntervalsFormat {

    private String type;
    private int rounds;
    private int workMin;
    private int restMin;
}
