package com.romm.todopp.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "tb_participation") @Entity @NoArgsConstructor @Setter @Getter @ToString
public class Participation {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne @JoinColumn(nullable = false) 
    private User participant;

    @ManyToOne @JoinColumn(nullable = false) 
    private TaskList taskList;

    @Column(nullable = false) 
    Instant createdAt;
}
