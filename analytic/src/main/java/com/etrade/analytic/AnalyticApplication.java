package com.etrade.analytic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AnalyticApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyticApplication.class, args);
    }

}
