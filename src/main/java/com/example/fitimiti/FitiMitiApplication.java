package com.example.fitimiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/index")
    public String helloWorld() {
        return "index";
    }

}
@Controller
class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }
}