package com.example.fitimiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestFitiMitiApplication {

    public static void main(String[] args) {
        SpringApplication.from(FitiMitiApplication::main).with(TestFitiMitiApplication.class).run(args);
    }

}
