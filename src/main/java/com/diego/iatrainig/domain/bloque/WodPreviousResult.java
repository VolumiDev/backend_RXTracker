package com.diego.iatrainig.domain.bloque;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WodPreviousResult {

    private LocalDate date;
    private Integer totalReps;
    private String scaling;
}
