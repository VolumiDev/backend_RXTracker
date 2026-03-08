package com.diego.iatrainig.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mapping.model.SnakeCaseFieldNamingStrategy;

import java.util.Collections;

@Configuration
@EnableMongoAuditing
public class MongoConfig {

    @Bean
    public MongoMappingContext mongoMappingContext() throws ClassNotFoundException {
        MongoMappingContext context = new MongoMappingContext();
        context.setFieldNamingStrategy(new SnakeCaseFieldNamingStrategy());
        context.setAutoIndexCreation(true);
        return context;
    }
}