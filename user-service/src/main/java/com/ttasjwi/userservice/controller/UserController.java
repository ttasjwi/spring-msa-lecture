package com.ttasjwi.userservice.controller;

import com.ttasjwi.userservice.vo.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class UserController {

//    private final Environment env;
    private final Greeting greeting;

    @GetMapping("/health-check")
    public String status() {
        return "It's working in USER-SERVICE";
    }

    @GetMapping("/welcome")
    public String welcome() {
         //return env.getProperty("greeting.message");
        return greeting.getMessage();
    }
}
