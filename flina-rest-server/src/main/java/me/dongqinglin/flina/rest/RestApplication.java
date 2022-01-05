package me.dongqinglin.flina.rest;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class RestApplication {

    public static void main(String[] args) {
        try{
            SpringApplication.run(RestApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
