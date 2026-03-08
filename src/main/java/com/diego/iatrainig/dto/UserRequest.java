package com.diego.iatrainig.dto;

import com.diego.iatrainig.domain.AthleteLevel;
import com.diego.iatrainig.domain.Role;
import jakarta.validation.constraints.*;

/**
 * Body for creating or updating a user.
 */
public record UserRequest(

        @NotBlank
        String name,

        @Email @NotBlank
        String email,

        @NotBlank @Size(min = 8)
        String password,

        @NotNull
        AthleteLevel level,

        @NotNull
        Role role,

        @Positive
        Double weightKg,

        @Positive
        Double heightCm,

        @Min(10) @Max(100)
        Integer age
) {}
