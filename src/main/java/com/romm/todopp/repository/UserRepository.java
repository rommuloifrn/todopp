package com.romm.todopp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romm.todopp.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
