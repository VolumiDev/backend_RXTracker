package com.diego.iatrainig.domain.bloque;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WodCompetitionBlock extends Block {

    private String wodName;

    @JsonProperty("is_open")
    private boolean isOpen;

    private String openEdition;
    private String status;
    private String officialDescription;
    private WodPreviousResult previousResult;
}
