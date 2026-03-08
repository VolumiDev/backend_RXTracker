package com.diego.iatrainig.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;

    private AthleteLevel level;

    private Role role;

    @Field("weight_kg")
    private Double weightKg;

    @Field("height_cm")
    private Integer heightCm;

    private Integer age;

    @Field("registered_at")
    private LocalDateTime registeredAt;
}
