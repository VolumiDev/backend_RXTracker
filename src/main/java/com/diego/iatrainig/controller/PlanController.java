package com.diego.iatrainig.controller;

import com.diego.iatrainig.dto.WeeklyPlanRequest;
import com.diego.iatrainig.dto.WeeklyPlanResponse;
import com.diego.iatrainig.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/planes")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    // (1) POST /api/v1/planes — receives Gemini JSON, returns 201 with Location header
    @PostMapping
    public ResponseEntity<WeeklyPlanResponse> savePlan(@Valid @RequestBody WeeklyPlanRequest request) {
        WeeklyPlanResponse response = planService.savePlan(request);

        // (2) Build the URI of the newly created resource: /api/v1/planes/{id}
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    // GET /api/v1/planes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<WeeklyPlanResponse> getPlanById(@PathVariable String id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    // (3) GET /api/v1/planes?userId=... — filter by user
    @GetMapping
    public ResponseEntity<List<WeeklyPlanResponse>> getPlansByUser(@RequestParam String userId) {
        return ResponseEntity.ok(planService.getPlansByUser(userId));
    }
}
