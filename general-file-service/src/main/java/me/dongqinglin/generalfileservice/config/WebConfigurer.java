package me.dongqinglin.generalfileservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfigurer implements WebMvcConfigurer {
    @Value("${personal-upload-root}")
    private String UPLOAD_ROOT;

    @Value("${personal-static-root}")
    private String STATIC_ROOT;

    public WebConfigurer() {
        super();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("file:" + STATIC_ROOT)
                .addResourceLocations("file:" + UPLOAD_ROOT);
        WebMvcConfigurer.super.addResourceHandlers(registry);
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