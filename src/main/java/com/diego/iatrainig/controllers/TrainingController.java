package com.diego.iatrainig.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrainingController {

    @GetMapping("/trainings")
    public String getTrainigs() {
        return "Traininnnngs";
    }
}
