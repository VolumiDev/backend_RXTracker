package com.diego.iatrainig.service.impl;

import com.diego.iatrainig.domain.*;
import com.diego.iatrainig.dto.WorkoutImportRequest;
import com.diego.iatrainig.dto.WorkoutResponse;
import com.diego.iatrainig.dto.WorkoutSetDto;
import com.diego.iatrainig.repository.ExerciseRepository;
import com.diego.iatrainig.repository.UserStatsRepository;
import com.diego.iatrainig.repository.WorkoutRepository;
import com.diego.iatrainig.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;
    
    @Autowired
    private UserStatsRepository userStatsRepository;
    
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    @Transactional
    public WorkoutResponse importWorkout(WorkoutImportRequest request) {
        Workout workout = new Workout();
        workout.setUserId(request.userId());
        workout.setDatePlanned(request.datePlanned());
        workout.setDateCompleted(request.dateCompleted());
        workout.setStatus(request.dateCompleted() != null ? WorkoutStatus.COMPLETED : WorkoutStatus.PENDING);
        
        List<WorkoutSet> sets = request.sets().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
        workout.setSets(sets);
        
        Workout savedWorkout = workoutRepository.save(workout);
        
        if (savedWorkout.getStatus() == WorkoutStatus.COMPLETED) {
            updateUserStats(savedWorkout);
        }
        
        return mapToResponse(savedWorkout);
    }
    
    private void updateUserStats(Workout workout) {
        UserStats userStats = userStatsRepository.findByUserId(workout.getUserId())
                .orElseGet(() -> {
                    UserStats newStats = new UserStats();
                    newStats.setUserId(workout.getUserId());
                    newStats.setPersonalRecords(new ArrayList<>());
                    return newStats;
                });
        
        boolean statsUpdated = false;
        
        for (WorkoutSet set : workout.getSets()) {
            if (set.getActualWeight() > 0) {
                Optional<PersonalRecord> existingRecord = userStats.getPersonalRecords().stream()
                        .filter(pr -> pr.getExerciseId().equals(set.getExerciseId()))
                        .findFirst();
                
                if (existingRecord.isPresent()) {
                    PersonalRecord record = existingRecord.get();
                    if (set.getActualWeight() > record.getOneRepMax()) {
                        record.setOneRepMax(set.getActualWeight());
                        record.setAchievedAt(workout.getDateCompleted());
                        statsUpdated = true;
                    }
                } else {
                    PersonalRecord newRecord = new PersonalRecord();
                    newRecord.setExerciseId(set.getExerciseId());
                    newRecord.setOneRepMax(set.getActualWeight());
                    newRecord.setAchievedAt(workout.getDateCompleted());
                    
                    // Fetch exercise name for denormalization
                    String exerciseName = exerciseRepository.findById(set.getExerciseId())
                            .map(Exercise::getName)
                            .orElse("Ejercicio Desconocido");
                    newRecord.setExerciseName(exerciseName);
                    
                    userStats.getPersonalRecords().add(newRecord);
                    statsUpdated = true;
                }
            }
        }
        
        if (statsUpdated) {
            userStatsRepository.save(userStats);
        }
    }
    
    private WorkoutSet mapToDomain(WorkoutSetDto dto) {
        WorkoutSet set = new WorkoutSet();
        set.setExerciseId(dto.exerciseId());
        set.setOrder(dto.order());
        set.setTargetReps(dto.targetReps());
        set.setTargetWeight(dto.targetWeight());
        set.setActualReps(dto.actualReps() != null ? dto.actualReps() : 0);
        set.setActualWeight(dto.actualWeight() != null ? dto.actualWeight() : 0.0);
        set.setRpe(dto.rpe() != null ? dto.rpe() : 0);
        return set;
    }
    
    private WorkoutResponse mapToResponse(Workout workout) {
        List<WorkoutSetDto> setDtos = workout.getSets().stream()
                .map(set -> new WorkoutSetDto(
                        set.getExerciseId(),
                        set.getOrder(),
                        set.getTargetReps(),
                        set.getTargetWeight(),
                        set.getActualReps(),
                        set.getActualWeight(),
                        set.getRpe()
                ))
                .collect(Collectors.toList());
                
        return new WorkoutResponse(
                workout.getId(),
                workout.getUserId(),
                workout.getDatePlanned(),
                workout.getDateCompleted(),
                workout.getStatus(),
                setDtos
        );
    }
}
