package com.diego.iatrainig.dto;

import com.diego.iatrainig.domain.AthleteLevel;
import com.diego.iatrainig.domain.Role;

import java.time.LocalDateTime;

/**
 * Response body for user data. Never exposes the password.
 */
public record UserResponse(
        String id,
        String name,
        String email,
        AthleteLevel level,
        Role role,
        Double weightKg,
        Double heightCm,
        Integer age,
        LocalDateTime registeredAt
) {}
