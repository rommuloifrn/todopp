package com.romm.todopp.entity;

import java.time.Instant;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "tb_task") @Entity @NoArgsConstructor @Setter @Getter @ToString
public class Task {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private User owner;

    @Column(length = 30, nullable = false)
    private String title;

    //private boolean finished;

    private Date deadline;

    @ManyToOne @JoinColumn(nullable = false)
    private TaskList taskList;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(columnDefinition = "boolean default false")
    private boolean isPublic;
}
