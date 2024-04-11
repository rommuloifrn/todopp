package com.romm.todopp.service;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.romm.todopp.DTO.TaskListUpdateDTO;
import com.romm.todopp.entity.TaskList;
import com.romm.todopp.repository.TaskListRepository;
import com.romm.todopp.repository.TaskRepository;
import com.romm.todopp.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskListService {

    @Autowired TaskListRepository taskListRepository;
    @Autowired TaskRepository taskRepository;
    @Autowired UserRepository userRepository;

    public void create(TaskList taskList) {
        Long ownerId = new Random().nextLong(1, 4);
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
        taskRepository.deleteAllByTaskList(taskList);
        taskListRepository.delete(taskList);
    }

    private TaskList findOr404(Long id) {
        Optional<TaskList> opt = taskListRepository.findById(id);
        if (opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else return opt.get();
    }
}
