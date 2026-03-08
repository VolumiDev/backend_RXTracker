package com.diego.iatrainig.dto;

import com.diego.iatrainig.domain.AthleteSnapshot;
import com.diego.iatrainig.domain.TrainingDay;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Response body returned after saving a weekly plan.
 */
public record WeeklyPlanResponse(
        String id,
        String userId,
        int weekNumber,
        int mesocycle,
        String phase,
        String objective,
        LocalDate startDate,
        LocalDate endDate,
        LocalDateTime loadedAt,
        AthleteSnapshot athlete,
        List<TrainingDay> trainingDays
) {}
