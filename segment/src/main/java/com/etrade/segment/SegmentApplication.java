package com.etrade.segment;

import com.etrade.segment.service.SegmentService;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class SegmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(SegmentApplication.class, args);
	}

	@Bean
	public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
		return new KeycloakSpringBootConfigResolver();
	}

	@Bean
	CommandLineRunner run(SegmentService segmentService) {
		return args -> {
			segmentService.cacheSegments();
		};
	}

}
