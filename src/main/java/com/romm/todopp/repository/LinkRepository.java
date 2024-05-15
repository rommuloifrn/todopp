package com.romm.todopp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romm.todopp.entity.Link;
import com.romm.todopp.entity.Task;
import com.romm.todopp.entity.TaskList;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    public List<Link> findAllByTaskListOrderByTaskListPosition(TaskList taskList);

    public Optional<Link> findByTaskListAndTaskListPosition(TaskList taskList, int taskListPosition);

    public Optional<Link> findByTaskListIdAndTaskId(Long taskListId, Long taskId);

    public Optional<Link> findByTaskListAndTask(TaskList taskList, Task task);
}
