package com.romm.todopp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romm.todopp.entity.Link;
import com.romm.todopp.entity.TaskList;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    public List<Link> findAllByTaskListOrderByTaskListPosition(TaskList taskList);
}
