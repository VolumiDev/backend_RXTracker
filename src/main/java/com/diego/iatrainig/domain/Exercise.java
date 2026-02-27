package com.diego.iatrainig.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "exercises")
public class Exercise {
    @Id
    private String id;
    private String name;
    private String muscleGroup;
    private String type;
}
