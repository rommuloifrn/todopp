package com.romm.todopp.service;

import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.romm.todopp.DTO.TaskListUpdateDTO;
import com.romm.todopp.entity.Link;
import com.romm.todopp.entity.Task;
import com.romm.todopp.entity.TaskList;
import com.romm.todopp.repository.TaskListRepository;
import com.romm.todopp.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskListService {

    @Autowired TaskListRepository taskListRepository;
    @Autowired TaskService taskService;
    @Autowired UserRepository userRepository;

    public void create(TaskList taskList) {
        Long ownerId = new Random().nextLong(1, 2);
        taskList.setCreatedAt(Instant.now());
        taskList.setOwner(userRepository.findById(ownerId).get());
        taskListRepository.save(taskList);
    }

    public TaskList read(Long id) throws ResponseStatusException {
        TaskList taskList = findOr404(id);
        return taskList;
    }

    public void edit(TaskListUpdateDTO data, Long id) throws ResponseStatusException {
        TaskList taskList = findOr404(id);
        
        if (data.title() != null) taskList.setTitle(data.title());
        if (data.description() != null) taskList.setDescription(data.description());

        taskListRepository.save(taskList);
    }

    @Transactional // depois estudar as formas de fazer cascateamento... Não sei direito o que isso aqui faz, mas resolve meu problema.
    public void delete(Long id) throws ResponseStatusException {
        TaskList taskList = findOr404(id);
        taskList.getLinks().forEach((Link link) -> { // caso a task só tenha um link, deleta ela junto.
            taskService.deleteIfSingleLink(link.getTask());
        });
        taskListRepository.delete(taskList);
    }

    public List<TaskList> findAll() {
        return taskListRepository.findAll();
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

    private TaskList findOr404(Long id) {
        Optional<TaskList> opt = taskListRepository.findById(id);
        if (opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else return opt.get();
    }
}
