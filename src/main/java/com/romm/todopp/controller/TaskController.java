package com.romm.todopp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.romm.todopp.entity.Task;
import com.romm.todopp.service.TaskService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller @RequestMapping("/task")
public class TaskController {

    @Autowired TaskService taskService;
    
    @PostMapping("/create/{taskListId}")
    public String create(Task task, @PathVariable Long taskListId) {
        taskService.create(task, taskListId);
        return "redirect:/lists/" + taskListId;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        return "redirect:/lists/" + taskService.delete(id);
    }
    
}
