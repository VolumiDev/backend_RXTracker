package com.diego.iatrainig.dto;

import com.diego.iatrainig.domain.WorkoutStatus;
import java.time.LocalDate;
import java.util.List;

public record WorkoutResponse(
    String id,
    String userId,
    LocalDate datePlanned,
    LocalDate dateCompleted,
    WorkoutStatus status,
    List<WorkoutSetDto> sets
) {}
