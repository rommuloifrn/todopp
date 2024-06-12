package com.romm.todopp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.romm.todopp.entity.Link;
import com.romm.todopp.entity.Task;
import com.romm.todopp.entity.TaskList;
import com.romm.todopp.service.LinkService;
import com.romm.todopp.service.TaskListService;
import com.romm.todopp.service.TaskService;




@Controller @RequestMapping("/task")
public class TaskController {

    @Autowired TaskListService taskListService;
    @Autowired TaskService taskService;
    @Autowired LinkService linkService;
    
    @PostMapping("/create/{taskListId}") // refatorar isso aqui pra n precisar enviar o id dpss
    public String create(Task task, @PathVariable Long taskListId) throws ResponseStatusException {
        var taskList = taskListService.read(taskListId);
        taskService.create(task, taskList);
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

    @PostMapping("/up/{linkId}/backto/{taskListId}")
    public String upTask(@PathVariable Long linkId, @PathVariable Long taskListId) throws ResponseStatusException {
        Link link = linkService.read(linkId);
        linkService.positionUp(link);
        
        return "redirect:/lists/" + taskListId;
    }

    @PostMapping("/down/{linkId}/backto/{taskListId}")
    public String downTask(@PathVariable Long linkId, @PathVariable Long taskListId) throws ResponseStatusException {
        Link link = linkService.read(linkId);
        linkService.positionDown(link);
        
        return "redirect:/lists/" + taskListId;
    }

    @GetMapping("/link/{taskId}/backto/{originTaskListId}")
    public ModelAndView linkTask(@PathVariable Long taskId, @PathVariable Long originTaskListId) {
        List<TaskList> taskLists = taskListService.findAll();
        var task = taskService.read(taskId);
        return new ModelAndView("link/linktask", Map.of("taskLists", taskLists, "task", task, "originTaskListId", originTaskListId));
    }

    @PostMapping("/{taskId}/linkto/{taskListId}/backto/{originTaskListId}")
    public String linkTask(@PathVariable Long taskId, @PathVariable Long taskListId, @PathVariable Long originTaskListId) {
        var task = taskService.read(taskId);
        var taskList = taskListService.read(taskListId);
        linkService.create(task, taskList);
        
        return "redirect:/lists/" + originTaskListId;
    }
    
    @PostMapping("/{taskId}/unlink-of/{taskListId}")
    public String unlink(@PathVariable Long taskId, @PathVariable Long taskListId) {
        var task = taskService.read(taskId);
        var taskList = taskListService.read(taskListId);
        taskService.unlink(task, taskList);
        
        return "redirect:/lists/" + taskListId;
    }
    
}
