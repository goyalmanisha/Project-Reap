package com.projectreap.ProjectReap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.projectreap.ProjectReap.repository")
@EntityScan(basePackages = {"com.projectreap.ProjectReap.entity"})
public class ProjectReapApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectReapApplication.class, args);
	}
}
