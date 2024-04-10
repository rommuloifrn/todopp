package com.romm.todopp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.romm.todopp.repository.TaskListRepository;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/lists")
public class TaskListController {

    @Autowired TaskListRepository taskListRepository;

    @GetMapping("")
    public ModelAndView lists() {
        return new ModelAndView("lists", Map.of("tasklists", taskListRepository.findAll()));
    }
    
}
