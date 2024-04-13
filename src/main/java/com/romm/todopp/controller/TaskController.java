package com.romm.todopp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.romm.todopp.entity.Task;
import com.romm.todopp.service.LinkService;
import com.romm.todopp.service.TaskService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller @RequestMapping("/task")
public class TaskController {

    @Autowired TaskService taskService;
    @Autowired LinkService linkService;
    
    @PostMapping("/create/{taskListId}") // refatorar isso aqui pra n precisar enviar o id dpss
    public String create(Task task, @PathVariable Long taskListId) throws ResponseStatusException {
        taskService.create(task, taskListId);
        linkService.create(task, taskListId);
        return "redirect:/lists/" + taskListId;
    }

    @PostMapping("/delete/{id}/backto/{taskListId}")
    public String delete(@PathVariable Long id, @PathVariable Long taskListId) throws ResponseStatusException {
        taskService.delete(id);
        return "redirect:/lists/" + taskListId;
    }

    @PostMapping("/finish/{id}/backto/{taskListId}")
    public String finish(@PathVariable Long id, @PathVariable Long taskListId) throws ResponseStatusException {
        taskService.toggleFinish(id);
        return "redirect:/lists/" + taskListId;
    }
    
    
}
