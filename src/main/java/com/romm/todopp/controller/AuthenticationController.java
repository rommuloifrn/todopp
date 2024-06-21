package com.romm.todopp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.romm.todopp.DTO.RegistrationDTO;
import com.romm.todopp.exceptions.UsernameAlreadyExistsException;
import com.romm.todopp.service.AuthenticationService;






@Controller
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Autowired AuthenticationService authenticationService;
    //@Autowired UserService userService;
    
    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(RegistrationDTO data) throws UsernameAlreadyExistsException {
        authenticationService.register(data);
        return "redirect:auth/login";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    
    @GetMapping("/login-error")
    public String login(Model model) {
        model.addAttribute("loginError", true);
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "auth/logged_out";
    }
    

    // @PostMapping("/login")
    // public String login(LoginDTO data) throws UsernameNotFoundException, RuntimeException { // estudar depois
    //     if (authenticationService.login(data) == null) return "redirect:/auth/login";
        
    //     return "redirect:/";
    // }
    
    
    
    
}
