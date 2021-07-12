package com.zensar.olxgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OlxGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlxGatewayApplication.class, args);
	}

}
