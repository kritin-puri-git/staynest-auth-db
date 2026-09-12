package com.project.staynest.auth.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class StaynestMigrationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(StaynestMigrationsApplication.class, args);
	}

}
