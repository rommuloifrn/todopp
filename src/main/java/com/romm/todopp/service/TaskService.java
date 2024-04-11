package com.romm.todopp.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.romm.todopp.DTO.TaskDTO;
import com.romm.todopp.entity.Task;
import com.romm.todopp.entity.User;
import com.romm.todopp.repository.TaskListRepository;
import com.romm.todopp.repository.TaskRepository;
import com.romm.todopp.repository.UserRepository;

@Service
public class TaskService {
    
    @Autowired TaskRepository taskRepository;
    @Autowired UserRepository userRepository;
    @Autowired TaskListRepository taskListRepository;

    public void create(Task task, Long taskListId) {
        task.setCreatedAt(Instant.now());
        task.setTaskList(taskListRepository.findById(taskListId).get());
        User defaultUser = userRepository.findById(Long.valueOf(1)).get();
        task.setOwner(defaultUser);

        taskRepository.save(task);
    }

    public void update(TaskDTO data, Long id) {
        Task task = findOr404(id);
        if (data.title() != null) task.setTitle(data.title());
        taskRepository.save(task);
    }

    public Long delete(Long id) { 
        Task task = findOr404(id);
        Long listId = task.getTaskList().getId();
        taskRepository.delete(task);
        return listId; // retorna taskListId pra poder redirecionar pra a página da lista
    }

    public void toggleFinish(Long id) throws ResponseStatusException {
        Task task = findOr404(id);
        task.setFinished(!task.isFinished());
        taskRepository.save(task);
    }

    public List<Task> findFromTaskList(Long id) {
        //return taskListRepository.findById(id).get().getTasks();
        return taskRepository.findAllByTaskListId(id);
    }

    private Task findOr404(Long id) {
        Optional<Task> opt = taskRepository.findById(id);
        if (opt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else return opt.get();
    }
}
