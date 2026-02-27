package com.diego.iatrainig.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record WorkoutImportRequest(
    @NotBlank(message = "El ID de usuario es obligatorio")
    String userId,
    
    @NotNull(message = "La fecha planificada es obligatoria")
    LocalDate datePlanned,
    
    LocalDate dateCompleted,
    
    @Valid
    List<WorkoutSetDto> sets
) {}
