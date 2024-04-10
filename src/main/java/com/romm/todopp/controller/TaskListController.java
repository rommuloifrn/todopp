package com.romm.todopp.controller;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.romm.todopp.DTO.TaskListDTO;
import com.romm.todopp.entity.Task;
import com.romm.todopp.entity.TaskList;
import com.romm.todopp.repository.TaskListRepository;
import com.romm.todopp.repository.TaskRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
@RequestMapping("/lists")
public class TaskListController {

    @Autowired TaskListRepository taskListRepository;
    @Autowired TaskRepository taskRepository;

    @GetMapping("")
    public ModelAndView lists() {
        return new ModelAndView("lists", Map.of("tasklists", taskListRepository.findAll()));
    }

    @GetMapping("/new")
    public ModelAndView create() {
        return new ModelAndView("tasklist/create");
    }
    
    @PostMapping("/new")
    public String create(TaskList taskList) {
        taskList.setCreatedAt(Instant.now());
        taskListRepository.save(taskList);
        
        return new String("redirect:/lists");
    }

    @GetMapping("/{id}")
    public ModelAndView read(@PathVariable Long id) {
        Optional<TaskList> opt = taskListRepository.findById(id);
        if (opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        TaskList taskList = opt.get();
        List<Task> tasks = taskRepository.findAllByTaskListId(id);
        System.out.println(taskList);
        return new ModelAndView("tasklist/read", Map.of("tasklist", taskList, "tasks", tasks));
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        Optional<TaskList> opt = taskListRepository.findById(id);
        if (opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        TaskList taskList = opt.get();
        
        return new ModelAndView("tasklist/delete", Map.of("tasklist", taskList));
    }

    @PostMapping("/delete/{id}") // isso aqui recebe um form
    public String delete(TaskList taskList) {
        taskListRepository.delete(taskList);

        return "redirect:/lists";
    }

    @GetMapping("/edit/{id}") // isso aqui não, por isso nao da pra pegar a taskList direto nos parametros
    public ModelAndView edit(@PathVariable Long id) {
        Optional<TaskList> opt = taskListRepository.findById(id);
        if (opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        TaskList taskList = opt.get();
        return new ModelAndView("tasklist/create", Map.of("tasklist", taskList));
    }

    @PostMapping("/edit/{id}")
    public String edit(TaskListDTO data, @PathVariable Long id) {
        Optional<TaskList> opt = taskListRepository.findById(id);
        if (opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        TaskList taskList = opt.get();
        
        if (data.title() != null) taskList.setTitle(data.title());
        if (data.description() != null) taskList.setDescription(data.description());

        taskListRepository.save(taskList);
        return "redirect:/lists";
    }
    
    
    
}
