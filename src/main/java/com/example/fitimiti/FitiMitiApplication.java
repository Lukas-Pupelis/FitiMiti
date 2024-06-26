package com.example.fitimiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@EnableAspectJAutoProxy
public class FitiMitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FitiMitiApplication.class, args);
        System.out.println("Hello, world!");
    }

}

@RestController
class HelloWorldController {

    @GetMapping("/index")
    public String helloWorld() {
        return "index";
    }

}