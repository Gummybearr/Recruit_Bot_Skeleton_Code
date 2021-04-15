package com.gummybearr.jai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JaiApplication {
    public static void main(String[] args) {
        SpringApplication.run(JaiApplication.class, args);
    }
}
