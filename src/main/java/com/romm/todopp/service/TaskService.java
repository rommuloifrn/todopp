package com.romm.todopp.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.romm.todopp.DTO.TaskDTO;
import com.romm.todopp.entity.Task;
import com.romm.todopp.entity.User;
import com.romm.todopp.repository.TaskRepository;
import com.romm.todopp.repository.UserRepository;

@Service
public class TaskService {
    
    @Autowired TaskRepository taskRepository;
    @Autowired UserRepository userRepository;
    @Autowired TaskListService taskListService;

    public void create(Task task, Long taskListId) {
        task.setCreatedAt(Instant.now());
        task.setTaskList(taskListService.read(taskListId));
        User defaultUser = userRepository.findById(Long.valueOf(1)).get();
        task.setOwner(defaultUser);

        taskRepository.save(task);
    }

    public void update(TaskDTO data, Long id) throws ResponseStatusException {
        Task task = findOr404(id);
        if (data.title() != null) task.setTitle(data.title());
        taskRepository.save(task);
    }

    public Task delete(Long id) throws ResponseStatusException { 
        Task task = findOr404(id);
        taskRepository.delete(task);
        return task;
    }

    public Task toggleFinish(Long id) throws ResponseStatusException {
        Task task = findOr404(id);
        task.setFinished(!task.isFinished());
        taskRepository.save(task);
        return task;
    }

    public List<Task> findFromTaskList(Long id) {
        return taskListService.read(id).getTasks();
    }

    private Task findOr404(Long id) {
        Optional<Task> opt = taskRepository.findById(id);
        if (opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else return opt.get();
    }
}
