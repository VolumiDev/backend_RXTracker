package com.diego.iatrainig.controllers;

import com.diego.iatrainig.dto.WorkoutImportRequest;
import com.diego.iatrainig.dto.WorkoutResponse;
import com.diego.iatrainig.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workouts")
@CrossOrigin(origins = "*")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @PostMapping("/import")
    public ResponseEntity<WorkoutResponse> importWorkout(@Valid @RequestBody WorkoutImportRequest request) {
        WorkoutResponse response = workoutService.importWorkout(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
