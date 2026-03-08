package com.diego.iatrainig.service.impl;

import com.diego.iatrainig.domain.DaySession;
import com.diego.iatrainig.domain.SessionStatus;
import com.diego.iatrainig.domain.TrainingDay;
import com.diego.iatrainig.domain.WeeklyPlan;
import com.diego.iatrainig.dto.WeeklyPlanRequest;
import com.diego.iatrainig.dto.WeeklyPlanResponse;
import com.diego.iatrainig.exception.ResourceNotFoundException;
import com.diego.iatrainig.repository.DaySessionRepository;
import com.diego.iatrainig.repository.WeeklyPlanRepository;
import com.diego.iatrainig.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor  // (1)
public class PlanServiceImpl implements PlanService {

    private final WeeklyPlanRepository planRepository;
    private final DaySessionRepository sessionRepository;

    @Override
    public WeeklyPlanResponse savePlan(WeeklyPlanRequest request) {

        // (2) Build the WeeklyPlan document from the incoming DTO
        WeeklyPlan plan = new WeeklyPlan();
        plan.setUserId(request.userId());
        plan.setWeekNumber(request.weekNumber());
        plan.setMesocycle(request.mesocycle());
        plan.setPhase(request.phase());
        plan.setObjective(request.objective());
        plan.setStartDate(request.startDate());
        plan.setEndDate(request.endDate());
        plan.setLoadedAt(LocalDateTime.now());
        plan.setAthlete(request.athlete());
        plan.setTrainingDays(request.trainingDays());

        WeeklyPlan saved = planRepository.save(plan);

        // (3) For each training day, create one DaySession document
        List<DaySession> sessions = request.trainingDays().stream()
                .map(day -> buildSession(saved, day))
                .toList();

        sessionRepository.saveAll(sessions);

        return toResponse(saved);
    }

    @Override
    public List<WeeklyPlanResponse> getPlansByUser(String userId) {
        return planRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public WeeklyPlanResponse getPlanById(String id) {
        WeeklyPlan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found: " + id)); // (4)
        return toResponse(plan);
    }

    // (5) Creates a pending DaySession from a TrainingDay
    private DaySession buildSession(WeeklyPlan plan, TrainingDay day) {
        DaySession session = new DaySession();
        session.setUserId(plan.getUserId());
        session.setWeekPlanId(plan.getId());
        session.setWeekNumber(plan.getWeekNumber());
        session.setMesocycle(plan.getMesocycle());
        session.setDay(day.getDay());
        session.setDate(resolveDate(plan.getStartDate(), day.getDay()));
        session.setSessionType(day.getSessionType());
        session.setStartTime(day.getStartTime());
        session.setStatus(SessionStatus.PENDING);
        session.setBlocks(day.getBlocks());
        return session;
    }

    /** Calculates the calendar date of a day given the Monday start date of the week. */
    private LocalDate resolveDate(LocalDate startDate, String day) {
        return switch (day.toLowerCase()) {
            case "monday"    -> startDate;
            case "tuesday"   -> startDate.plusDays(1);
            case "wednesday" -> startDate.plusDays(2);
            case "thursday"  -> startDate.plusDays(3);
            case "friday"    -> startDate.plusDays(4);
            case "saturday"  -> startDate.plusDays(5);
            case "sunday"    -> startDate.plusDays(6);
            default          -> startDate;
        };
    }

    // (6) Maps a WeeklyPlan domain object to its response DTO
    private WeeklyPlanResponse toResponse(WeeklyPlan plan) {
        return new WeeklyPlanResponse(
                plan.getId(),
                plan.getUserId(),
                plan.getWeekNumber(),
                plan.getMesocycle(),
                plan.getPhase(),
                plan.getObjective(),
                plan.getStartDate(),
                plan.getEndDate(),
                plan.getLoadedAt(),
                plan.getAthlete(),
                plan.getTrainingDays()
        );
    }
}
