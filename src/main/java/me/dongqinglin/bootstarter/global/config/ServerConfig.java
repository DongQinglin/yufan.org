package me.dongqinglin.bootstarter.global.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ServerConfig implements WebMvcConfigurer {

    public ServerConfig() {
        super();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("file:" + ServerConfigContrant.FILE_FOLDER);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    //     registry.addMapping("/**")
    //             .allowedOrigins("*")
    //             // .allowCredentials(true)
    //             .allowedMethods("GET","POST", "PUT", "DELETE")
    //             .allowedHeaders("*");
    // }
}
