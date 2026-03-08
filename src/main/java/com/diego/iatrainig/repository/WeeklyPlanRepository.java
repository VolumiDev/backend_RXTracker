package com.diego.iatrainig.repository;

import com.diego.iatrainig.domain.WeeklyPlan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklyPlanRepository extends MongoRepository<WeeklyPlan, String> {

    Optional<WeeklyPlan> findByWeekNumber(int weekNumber);
    List<WeeklyPlan> findByUserId(String userId);
    List<WeeklyPlan> findByMesocycleAndUserId(int mesocycle, String userId);
}
