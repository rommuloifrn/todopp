package com.romm.todopp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romm.todopp.entity.Invite;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {
    
}
