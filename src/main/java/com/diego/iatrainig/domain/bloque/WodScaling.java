package com.diego.iatrainig.domain.bloque;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WodScaling {

    private boolean rx;
    private List<String> scaledOptions;
    private List<String> mastersOptions;
}
