package com.jackson.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ElenaGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElenaGatewayApplication.class, args);
    }

}
