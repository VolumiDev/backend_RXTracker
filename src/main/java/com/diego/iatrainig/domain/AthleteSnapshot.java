package com.diego.iatrainig.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Immutable snapshot of the athlete's data at the moment a training plan was created.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AthleteSnapshot {

    private String name;
    private String level;
}
