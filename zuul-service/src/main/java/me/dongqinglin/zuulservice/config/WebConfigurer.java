package me.dongqinglin.zuulservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    public WebConfigurer() {
        super();
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Add mapping path
        // Which original domains are allowed
        // Whether to send cookie information
        // Which original domains are released (request method)
        // Which original domains are allowed (header information)
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET","POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }

}