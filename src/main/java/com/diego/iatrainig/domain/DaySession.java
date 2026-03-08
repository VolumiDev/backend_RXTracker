package com.diego.iatrainig.domain;

import com.diego.iatrainig.domain.bloque.Block;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * One training day with its planned blocks and the athlete's actual results.
 * This is the primary collection read by the Flutter client.
 */
@Data
@Document(collection = "sesion_dia")
@CompoundIndexes({
        @CompoundIndex(name = "user_date",     def = "{'user_id': 1, 'date': 1}"),
        @CompoundIndex(name = "week_day",      def = "{'week_number': 1, 'day': 1}"),
        @CompoundIndex(name = "status_date",   def = "{'status': 1, 'date': 1}"),
        @CompoundIndex(name = "exercise_date", def = "{'blocks.exercises.name': 1, 'date': -1}")
})
public class DaySession {

    @Id
    private String id;

    @Indexed
    private String userId;

    @Indexed
    private String weekPlanId;

    private int weekNumber;
    private int mesocycle;
    private String day;

    @Indexed
    private LocalDate date;

    private String sessionType;
    private String startTime;
    private SessionStatus status;
    private String postNotes;
    private Integer actualRpe;
    private LocalDateTime completedAt;
    private List<Block> blocks;
}
