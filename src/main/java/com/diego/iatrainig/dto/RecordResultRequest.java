package com.diego.iatrainig.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Body of PATCH /api/v1/sesiones/{id}/resultado.
 * The athlete submits actual results for a specific block.
 *
 * @param blockOrder  position of the block within the session (1-based)
 * @param result      free-form object matching the block_type result schema from docs/02_mongodb.md
 * @param actualRpe   session RPE reported by the athlete (1–10)
 * @param postNotes   optional notes after the session
 */
public record RecordResultRequest(

        @Min(1)
        int blockOrder,

        @NotNull
        Object result,

        @Min(1) @Max(10)
        Integer actualRpe,

        String postNotes
) {}
