package com.diego.iatrainig.domain.bloque;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "block_type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = WarmupBlock.class,         name = "warmup"),
        @JsonSubTypes.Type(value = CooldownBlock.class,       name = "cooldown"),
        @JsonSubTypes.Type(value = RestBlock.class,           name = "rest"),
        @JsonSubTypes.Type(value = StrengthBlock.class,       name = "strength"),
        @JsonSubTypes.Type(value = WodForTimeBlock.class,     name = "wod_for_time"),
        @JsonSubTypes.Type(value = WodAmrapBlock.class,       name = "wod_amrap"),
        @JsonSubTypes.Type(value = WodEmomBlock.class,        name = "wod_emom"),
        @JsonSubTypes.Type(value = WodIntervalsBlock.class,   name = "wod_intervalos"),
        @JsonSubTypes.Type(value = WodCompetitionBlock.class, name = "wod_competicion"),
        @JsonSubTypes.Type(value = WodTeamBlock.class,        name = "wod_team"),
        @JsonSubTypes.Type(value = AccessoryBlock.class,      name = "accessory"),
        @JsonSubTypes.Type(value = RecoveryBlock.class,       name = "recovery")
})
public abstract class Block {

    private int order;
    private Double targetRpe;
    private String heartRateZone;
    private Integer estimatedDurationMin;

    /** Null in plan_semana. Populated when the athlete registers results in sesion_dia. */
    private Object result;
}
