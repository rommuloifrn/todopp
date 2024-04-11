package com.romm.todopp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romm.todopp.entity.Task;
import com.romm.todopp.entity.TaskList;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findAllByTaskListId(Long id);

    public void deleteAllByTaskList(TaskList taskList);
}
