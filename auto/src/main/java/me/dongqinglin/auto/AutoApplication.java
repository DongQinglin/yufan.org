package me.dongqinglin.auto;


import me.dongqinglin.auto.filter.GatewayFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class AutoApplication {

    @Bean
    public GatewayFilter gatewayFilter(){
        return new GatewayFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(AutoApplication.class, args);
    }

}
