package com.romm.todopp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romm.todopp.entity.TaskList;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    
}
