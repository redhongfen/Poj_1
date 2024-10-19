package com.example.poj_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:8080")
@SpringBootApplication
public class Poj1Application {

    public static void main(String[] args) {
        SpringApplication.run(Poj1Application.class, args);
    }

}
