package com.diego.iatrainig.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record WorkoutSetDto(
    @NotBlank(message = "El ID del ejercicio es obligatorio")
    String exerciseId,
    
    @Min(value = 1, message = "El orden debe ser al menos 1")
    int order,
    
    @Min(value = 1, message = "Las repeticiones objetivo deben ser al menos 1")
    int targetReps,
    
    @NotNull(message = "El peso objetivo es obligatorio")
    Double targetWeight,
    
    Integer actualReps,
    
    Double actualWeight,
    
    Integer rpe
) {}
