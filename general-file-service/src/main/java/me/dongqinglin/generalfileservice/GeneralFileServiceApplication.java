package me.dongqinglin.generalfileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class GeneralFileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneralFileServiceApplication.class, args);
    }

}
