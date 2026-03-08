package com.diego.iatrainig.controller;

import com.diego.iatrainig.dto.DaySessionResponse;
import com.diego.iatrainig.dto.RecordResultRequest;
import com.diego.iatrainig.service.SessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sesiones")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    // (1) GET /api/v1/sesiones/hoy?userId=... — today's session for Flutter home screen
    @GetMapping("/hoy")
    public ResponseEntity<DaySessionResponse> getTodaySession(@RequestParam String userId) {
        return ResponseEntity.ok(sessionService.getTodaySession(userId));
    }

    // GET /api/v1/sesiones?weekPlanId=... — all sessions of a week
    @GetMapping
    public ResponseEntity<List<DaySessionResponse>> getSessionsByPlan(@RequestParam String weekPlanId) {
        return ResponseEntity.ok(sessionService.getSessionsByPlan(weekPlanId));
    }

    // (2) PATCH /api/v1/sesiones/{id}/resultado — athlete records a block result
    @PatchMapping("/{id}/resultado")
    public ResponseEntity<DaySessionResponse> recordResult(
            @PathVariable String id,
            @Valid @RequestBody RecordResultRequest request) {
        return ResponseEntity.ok(sessionService.recordResult(id, request));
    }

    // (3) PATCH /api/v1/sesiones/{id}/saltar — mark a session as skipped
    @PatchMapping("/{id}/saltar")
    public ResponseEntity<DaySessionResponse> skipSession(@PathVariable String id) {
        return ResponseEntity.ok(sessionService.skipSession(id));
    }
}
