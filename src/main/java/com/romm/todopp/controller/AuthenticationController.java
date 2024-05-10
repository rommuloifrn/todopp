package com.romm.todopp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping(value = "/auth")
public class AuthenticationController {
    
    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }
    
}
