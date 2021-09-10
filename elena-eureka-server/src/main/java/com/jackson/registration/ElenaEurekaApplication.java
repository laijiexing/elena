package com.jackson.registrationcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ElenaEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElenaEurekaApplication.class, args);
    }

}
