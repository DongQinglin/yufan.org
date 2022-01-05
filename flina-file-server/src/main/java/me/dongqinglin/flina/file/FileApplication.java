package me.dongqinglin.flina.file;


import me.dongqinglin.flina.file.middleware.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class FileApplication {

    public static void main(String[] args) {

        SpringApplication.run(FileApplication.class, args);

    }

}
