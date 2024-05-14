package com.romm.todopp.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.romm.todopp.DTO.TaskDTO;
import com.romm.todopp.entity.Task;
import com.romm.todopp.repository.TaskRepository;
import com.romm.todopp.repository.UserRepository;

@Service
public class TaskService {
    
    @Autowired TaskRepository taskRepository;
    @Autowired UserRepository userRepository;
    @Autowired AuthenticationService authenticationService;
    @Autowired AuthorizationService authorizationService;

    public Task create(Task task, Long taskListId) {
        task.setCreatedAt(Instant.now());
        task.setOwner(authenticationService.getPrincipal());
        return taskRepository.save(task);
    }

    public Task read(Long id) throws ResponseStatusException {
        Task task = accessAsUser(id);
        return task;
    }

    //refatorar isso aqui pra receber a Task direto depois...
    public void update(TaskDTO data, Long id) throws ResponseStatusException {
        Task task = accessAsUser(id);
        if (data.title() != null) task.setTitle(data.title());
        taskRepository.save(task);
    }

    public Task delete(Long id) throws ResponseStatusException { 
        Task task = accessAsUser(id);
        taskRepository.delete(task);
        return task;
    }

    public void deleteIfSingleLink(Task task) {
        if (task.getLinks().size()==1) delete(task.getId());
    }

    public Task toggleFinish(Long id) throws ResponseStatusException {
        Task task = accessAsUser(id);
        task.setFinished(!task.isFinished());
        taskRepository.save(task);
        return task;
    }

    public Task accessAsUser(Long id) throws ResponseStatusException {
        var task = findOr404(id);
        authorizationService.requestUserIsOwnerOrError(task);
        return task;
    }

    private Task findOr404(Long id) {
        Optional<Task> opt = taskRepository.findById(id);
        if (opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else return opt.get();
    }
}
