package com.diego.iatrainig.service;

import com.diego.iatrainig.dto.WorkoutImportRequest;
import com.diego.iatrainig.dto.WorkoutResponse;

public interface WorkoutService {
    WorkoutResponse importWorkout(WorkoutImportRequest request);
}
