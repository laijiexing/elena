package com.jackson.authentication;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.jackson.authentication.mappers")
@EnableDiscoveryClient
public class ElenaAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElenaAuthApplication.class, args);
    }

}
