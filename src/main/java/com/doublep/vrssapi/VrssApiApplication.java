package com.doublep.vrssapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VrssApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VrssApiApplication.class, args);
    }

}
