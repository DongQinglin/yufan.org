package me.dongqinglin.auto.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


    @EnableWebMvc
    @Configuration
    public class WebConfigurer implements WebMvcConfigurer {
        @Value("${file-service.profile}")
        private String uploadFilePath;

        public WebConfigurer() {
            super();
        }

        /**
         * 静态资源配置
         * @param registry 资源挂载器
         */
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**")
                    .addResourceLocations("classpath:/META-INF/resources/")
                    .addResourceLocations("classpath:/resources/")
                    .addResourceLocations("classpath:/static/")
                    .addResourceLocations("classpath:/public/")
                    .addResourceLocations("file:" + uploadFilePath);
            WebMvcConfigurer.super.addResourceHandlers(registry);
        }

        /**
         * 跨域配置
         * @param registry 跨域挂载器
         */
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            //添加映射路径
            registry.addMapping("/**")
                    //放行哪些原始域
                    .allowedOrigins("*")
                    //是否发送Cookie信息
                    .allowCredentials(true)
                    //放行哪些原始域(请求方式)
                    .allowedMethods("GET","POST", "PUT", "DELETE")
                    //放行哪些原始域(头部信息)
                    .allowedHeaders("*");
                    //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
                    //.exposedHeaders("Header1", "Header2");
        }
    }