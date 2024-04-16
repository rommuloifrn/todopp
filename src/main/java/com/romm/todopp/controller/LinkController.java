package com.romm.todopp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.romm.todopp.entity.Task;
import com.romm.todopp.entity.TaskList;
import com.romm.todopp.service.LinkService;
import com.romm.todopp.service.TaskListService;
import com.romm.todopp.service.TaskService;

@Controller @RequestMapping("/link")
public class LinkController {

    @Autowired LinkService linkService;
    @Autowired TaskListService taskListService;
    @Autowired TaskService taskService;

    @GetMapping("/{taskId}/backto/{originTaskListId}")
    public ModelAndView linkTask(@PathVariable Long taskId, @PathVariable Long originTaskListId) {
        List<TaskList> taskLists = taskListService.findAll();
        return new ModelAndView("link/linktask", Map.of("taskLists", taskLists, "taskId", taskId, "originTaskListId", originTaskListId));
    }

    @PostMapping("/{taskId}/to/{taskListId}/backto/{originTaskListId}")
    public String linkTask(@PathVariable Long taskId, @PathVariable Long taskListId, @PathVariable Long originTaskListId) {
        Task task = taskService.read(taskId);
        linkService.create(task, taskListId);
        
        return "redirect:/lists/" + originTaskListId;
    }

    @PostMapping("/{taskId}/unlink-of/{taskListId}")
    public String unlink(@PathVariable Long taskId, @PathVariable Long taskListId) {
        linkService.unlink(taskId, taskListId);
        
        return "redirect:/lists/" + taskListId;
    }
    
}
