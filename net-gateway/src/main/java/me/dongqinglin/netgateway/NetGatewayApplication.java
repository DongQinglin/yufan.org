package me.dongqinglin.netgateway;

import me.dongqinglin.netgateway.filter.UserTokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class NetGatewayApplication {
    @Bean
    public UserTokenFilter userTokenFilter(){
        return new UserTokenFilter();
    }

    public static void main(String[] args) {
        SpringApplication.run(NetGatewayApplication.class, args);
    }

}
