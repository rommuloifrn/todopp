package com.romm.todopp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.romm.todopp.DTO.TaskListReadDTO;
import com.romm.todopp.DTO.TaskListUpdateDTO;
import com.romm.todopp.entity.TaskList;
import com.romm.todopp.service.AuthenticationService;
import com.romm.todopp.service.LinkService;
import com.romm.todopp.service.TaskListService;
import com.romm.todopp.service.TaskService;




@Controller
@RequestMapping("/lists")
public class TaskListController {

    @Autowired TaskService taskService;
    @Autowired TaskListService taskListService;
    @Autowired LinkService linkService;
    @Autowired AuthenticationService authenticationService;

    @GetMapping("")
    public ModelAndView lists(Principal principal) {
        var requestingUser = authenticationService.getPrincipal();
        var listsProgresses = new ArrayList<String>();
        requestingUser.getTaskLists().forEach(list -> {
            listsProgresses.add(taskListService.getProgress(list));
        });
        return new ModelAndView("tasklist/lists", Map.of("principal", requestingUser, "progresses", listsProgresses));
    }

    @GetMapping("/new")
    public ModelAndView create() {
        return new ModelAndView("tasklist/create", Map.of("tasklist", new TaskList())); // envia tasklist vazia pra nao da erro de parsing no thymeleaf, assim podendo usar o mesmo template para create e edit
    }
    
    @PostMapping("/new")
    public String create(TaskList taskList) {
        taskListService.create(taskList);
        return new String("redirect:/lists");
    }

    @GetMapping("/new-sublist-on/{parentId}")
    public ModelAndView createSublist(@PathVariable Long parentId) {
        var parent = taskListService.read(parentId);
        return new ModelAndView("tasklist/create_sub", Map.of("parent", parent, "tasklist", new TaskList()));
    }

    @PostMapping("/new-sublist-on/{parentId}")
    public String createSublist(@PathVariable Long parentId, TaskList taskList) {
        taskListService.create(taskList, parentId);

        return "redirect:/lists/"+parentId;
    }

    @GetMapping("/{id}")
    public ModelAndView read(@PathVariable Long id) throws ResponseStatusException {
        TaskList taskList = taskListService.read(id);
        var progresses = new ArrayList<Boolean>();
        taskList.getChilds().forEach((sublist)->progresses.add(taskListService.isFinished(sublist)));

        TaskListReadDTO data = new TaskListReadDTO(taskList.getId(), taskList.getTitle(), taskList.getDescription(), taskList.getOwner().getUsername(), taskList.isPublic(), taskList.getDeadline(), taskList.getCreatedAt(), taskListService.getProgress(taskList), linkService.getFromTaskList(taskList), taskList.getChilds(), progresses);
        return new ModelAndView("tasklist/read", Map.of("tasklist", data));
    }

    @GetMapping("/edit/{id}") // isso aqui não, por isso nao da pra pegar a taskList direto nos parametros
    public ModelAndView update(@PathVariable Long id) throws ResponseStatusException {
        TaskList taskList = taskListService.read(id);
        return new ModelAndView("tasklist/create", Map.of("tasklist", taskList));
    }

    @PostMapping("/edit/{id}")
    public String edit(TaskListUpdateDTO data, @PathVariable Long id) throws ResponseStatusException {
        taskListService.edit(data, id);
        return "redirect:/lists";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteCheck(@PathVariable Long id) {
        TaskList taskList = taskListService.read(id);
        return new ModelAndView("tasklist/delete", Map.of("tasklist", taskList));
    }

    @PostMapping("/delete/{id}") // isso aqui recebe um form
    public String delete(@PathVariable Long id) {
        taskListService.delete(id);
        return "redirect:/lists";
    }
    
}
