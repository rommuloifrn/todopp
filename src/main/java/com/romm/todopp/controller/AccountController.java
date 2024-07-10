package com.romm.todopp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.romm.todopp.service.AccountService;


@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired AccountService accountService;

    @GetMapping
    public ModelAndView account() {
        return new ModelAndView("account/details.html", Map.of("account", accountService.AccountDetails()));
    }
    
}
