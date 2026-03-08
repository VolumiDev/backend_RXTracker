package com.diego.iatrainig.service;

import com.diego.iatrainig.dto.DaySessionResponse;
import com.diego.iatrainig.dto.RecordResultRequest;

import java.util.List;

public interface SessionService {

    /** Returns today's session for the given user. */
    DaySessionResponse getTodaySession(String userId);

    /** Returns all sessions for a given week plan. */
    List<DaySessionResponse> getSessionsByPlan(String weekPlanId);

    /** Records the athlete's actual result for one block and updates the session status. */
    DaySessionResponse recordResult(String sessionId, RecordResultRequest request);

    /** Marks a session as skipped. */
    DaySessionResponse skipSession(String sessionId);
}
