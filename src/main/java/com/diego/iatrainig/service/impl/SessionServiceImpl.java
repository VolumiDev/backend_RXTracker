package com.diego.iatrainig.service.impl;

import com.diego.iatrainig.domain.DaySession;
import com.diego.iatrainig.domain.SessionStatus;
import com.diego.iatrainig.domain.bloque.Block;
import com.diego.iatrainig.dto.DaySessionResponse;
import com.diego.iatrainig.dto.RecordResultRequest;
import com.diego.iatrainig.exception.ResourceNotFoundException;
import com.diego.iatrainig.repository.DaySessionRepository;
import com.diego.iatrainig.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final DaySessionRepository sessionRepository;

    @Override
    public DaySessionResponse getTodaySession(String userId) {
        // (1) "Today" is resolved server-side — the client never sends the date
        DaySession session = sessionRepository
                .findByUserIdAndDate(userId, LocalDate.now())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No session found for user " + userId + " today"));
        return toResponse(session);
    }

    @Override
    public List<DaySessionResponse> getSessionsByPlan(String weekPlanId) {
        return sessionRepository.findByWeekPlanId(weekPlanId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public DaySessionResponse recordResult(String sessionId, RecordResultRequest request) {
        DaySession session = findById(sessionId);

        // (2) Find the block by its order number and set the result
        List<Block> blocks = session.getBlocks();
        Block target = blocks.stream()
                .filter(b -> b.getOrder() == request.blockOrder())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Block with order " + request.blockOrder() + " not found"));

        target.setResult(request.result());

        // (3) Optional fields at session level
        if (request.actualRpe() != null) {
            session.setActualRpe(request.actualRpe());
        }
        if (request.postNotes() != null) {
            session.setPostNotes(request.postNotes());
        }

        // (4) Mark as completed when all blocks have a result
        boolean allDone = blocks.stream().allMatch(b -> b.getResult() != null);
        if (allDone) {
            session.setStatus(SessionStatus.COMPLETED);
            session.setCompletedAt(LocalDateTime.now());
        }

        return toResponse(sessionRepository.save(session));
    }

    @Override
    public DaySessionResponse skipSession(String sessionId) {
        DaySession session = findById(sessionId);
        session.setStatus(SessionStatus.SKIPPED);
        return toResponse(sessionRepository.save(session));
    }

    // --- private helpers ---

    private DaySession findById(String id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found: " + id));
    }

    private DaySessionResponse toResponse(DaySession session) {
        return new DaySessionResponse(
                session.getId(),
                session.getUserId(),
                session.getWeekPlanId(),
                session.getWeekNumber(),
                session.getMesocycle(),
                session.getDay(),
                session.getDate(),
                session.getSessionType(),
                session.getStartTime(),
                session.getStatus(),
                session.getPostNotes(),
                session.getActualRpe(),
                session.getCompletedAt(),
                session.getBlocks()
        );
    }
}
