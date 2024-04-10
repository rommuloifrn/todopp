package com.romm.todopp.entity;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "tb_task_list") @Entity @NoArgsConstructor @Setter @Getter @ToString
public class TaskList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(nullable = false)
    private User owner;

    @Column(nullable = false)
    private String title;

    private String description;

    private Date deadline;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Task> tasks;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(columnDefinition = "boolean default false")
    private boolean isPublic;
}
