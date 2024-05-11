package com.romm.todopp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.romm.todopp.DTO.RegistrationDTO;
import com.romm.todopp.exceptions.UsernameAlreadyExistsException;
import com.romm.todopp.service.AuthenticationService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Autowired AuthenticationService authenticationService;
    
    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(RegistrationDTO data) throws UsernameAlreadyExistsException {
        authenticationService.register(data);
        return "redirect:auth/login";
    }
    
    
}
