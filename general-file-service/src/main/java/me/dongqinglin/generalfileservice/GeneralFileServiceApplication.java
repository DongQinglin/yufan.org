package me.dongqinglin.generalfileservice;

import me.dongqinglin.generalfileservice.filter.GatewayFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class GeneralFileServiceApplication {

    @Bean
    public GatewayFilter gatewayFilter(){
        return new GatewayFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(GeneralFileServiceApplication.class, args);
    }

}
