package com.example.ms_estado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient

public class MsEstadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEstadoApplication.class, args);
	}

    // Agrega este bloque de código:
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
