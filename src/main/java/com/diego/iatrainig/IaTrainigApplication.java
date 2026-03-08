package com.diego.iatrainig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class IaTrainigApplication {

	public static void main(String[] args) {
		SpringApplication.run(IaTrainigApplication.class, args);
	}

}
