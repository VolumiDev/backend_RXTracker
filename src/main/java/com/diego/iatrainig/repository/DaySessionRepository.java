package com.diego.iatrainig.repository;

import com.diego.iatrainig.domain.DaySession;
import com.diego.iatrainig.domain.SessionStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DaySessionRepository extends MongoRepository<DaySession, String> {

    Optional<DaySession> findByUserIdAndDate(String userId, LocalDate date);
    List<DaySession> findByWeekNumber(int weekNumber);
    List<DaySession> findByWeekPlanId(String weekPlanId);
    List<DaySession> findByUserIdAndStatus(String userId, SessionStatus status);
}
