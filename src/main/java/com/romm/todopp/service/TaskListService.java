package com.romm.todopp.service;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.romm.todopp.DTO.TaskListUpdateDTO;
import com.romm.todopp.entity.Link;
import com.romm.todopp.entity.Task;
import com.romm.todopp.entity.TaskList;
import com.romm.todopp.entity.User;
import com.romm.todopp.repository.TaskListRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskListService {

    @Autowired TaskListRepository taskListRepository;

    @Autowired TaskService taskService;
    @Autowired AuthenticationService authenticationService;
    @Autowired AuthorizationService authorizationService;

    public void create(TaskList taskList) {
        taskList.setCreatedAt(Instant.now());
        taskList.setOwner(authenticationService.getPrincipal());
        taskListRepository.save(taskList);
    }

    public void create(TaskList taskList, Long parentId) {
        var parent = getAsUser(parentId);
        taskList.setParent(parent);

        create(taskList);
    }

    public TaskList read(Long id) throws ResponseStatusException {
        TaskList taskList = getAsUser(id);
        return taskList;
    }

    public void edit(TaskListUpdateDTO data, Long id) throws ResponseStatusException {
        TaskList taskList = getAsUser(id);
        
        if (data.title() != null) taskList.setTitle(data.title());
        if (data.description() != null) taskList.setDescription(data.description());

        taskListRepository.save(taskList);
    }

    @Transactional // depois estudar as formas de fazer cascateamento... Não sei direito o que isso aqui faz, mas resolve meu problema.
    public void delete(Long id) throws ResponseStatusException {
        TaskList taskList = getAsUser(id);

        taskList.getLinks().forEach((Link link) -> { // caso a task só tenha um link, deleta ela junto.
            taskService.deleteIfSingleLink(link.getTask());
        });
        taskListRepository.delete(taskList);
    }

    public List<TaskList> findAll() {
        User user = authenticationService.getPrincipal();

        return user.getTaskLists();
    }

    public String getProgress(TaskList taskList, boolean showByPercentage) {
        Iterator<Link> linksIterator = taskList.getLinks().iterator();
        int taskCount = 0, finishedCount = 0; Task actual;

        while (linksIterator.hasNext()) {
            actual = linksIterator.next().getTask();
            taskCount++;
            if (actual.isFinished()) finishedCount++;
        }
        
        if (taskCount == 0) return "no tasks";
        if (showByPercentage) return String.format("%.0f%%", (float) 100*finishedCount/taskCount);
        else return String.format("%d/%d", finishedCount, taskCount);
    }

    public String getProgress(TaskList taskList) {
        return getProgress(taskList, false);
    }

    private TaskList getAsUser(Long taskListId) throws ResponseStatusException {
        var taskList = findOr404(taskListId);
        authorizationService.requestUserIsOwnerOrError(taskList);
        return taskList;
    }

    private TaskList findOr404(Long id) {
        Optional<TaskList> opt = taskListRepository.findById(id);
        if (opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else return opt.get();
    }
}
