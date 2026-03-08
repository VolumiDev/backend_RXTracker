package com.diego.iatrainig.service;

import com.diego.iatrainig.dto.WeeklyPlanRequest;
import com.diego.iatrainig.dto.WeeklyPlanResponse;

import java.util.List;

public interface PlanService {

    /** Saves the Gemini-generated plan and creates one DaySession per training day. */
    WeeklyPlanResponse savePlan(WeeklyPlanRequest request);

    /** Returns all plans for a given user. */
    List<WeeklyPlanResponse> getPlansByUser(String userId);

    /** Returns a single plan by its id. */
    WeeklyPlanResponse getPlanById(String id);
}
