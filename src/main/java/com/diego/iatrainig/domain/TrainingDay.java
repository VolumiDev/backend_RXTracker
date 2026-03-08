package com.diego.iatrainig.domain;

import com.diego.iatrainig.domain.bloque.Block;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * One training day embedded in a WeeklyPlan document.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDay {

    private String day;
    private String sessionType;
    private String startTime;
    private String sessionDayId;
    private List<Block> blocks;
}
