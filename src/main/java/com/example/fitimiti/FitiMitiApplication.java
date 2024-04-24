package com.example.fitimiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class FitiMitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitiMitiApplication.class, args);
        System.out.println("Hello, world!");
    }

}

@RestController
class HelloWorldController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello, world!";
    }
}