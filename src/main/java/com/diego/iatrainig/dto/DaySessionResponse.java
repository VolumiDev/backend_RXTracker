package com.diego.iatrainig.dto;

import com.diego.iatrainig.domain.SessionStatus;
import com.diego.iatrainig.domain.bloque.Block;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Response body for a single training day session.
 * Primary payload consumed by the Flutter client.
 */
public record DaySessionResponse(
        String id,
        String userId,
        String weekPlanId,
        int weekNumber,
        int mesocycle,
        String day,
        LocalDate date,
        String sessionType,
        String startTime,
        SessionStatus status,
        String postNotes,
        Integer actualRpe,
        LocalDateTime completedAt,
        List<Block> blocks
) {}
