package com.diego.iatrainig.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "workouts")
public class Workout {
    @Id
    private String id;
    private String userId;
    private LocalDate datePlanned;
    private LocalDate dateCompleted;
    private WorkoutStatus status;
    private List<WorkoutSet> sets;
}
