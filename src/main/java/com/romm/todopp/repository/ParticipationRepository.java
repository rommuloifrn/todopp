package com.romm.todopp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romm.todopp.entity.Participation;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    
}
