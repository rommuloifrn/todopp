package com.romm.todopp.entity;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import com.romm.todopp.security.Ownable;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tb_task_list") @Entity @NoArgsConstructor @Setter @Getter
public class TaskList extends Ownable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Nonnull
    private User owner;

    @Column(nullable = false)
    private String title;

    private String description;

    private Date deadline;

    // @OneToMany(mappedBy = "taskList", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    // private List<Task> tasks;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(columnDefinition = "boolean default false")
    private boolean isPublic;

    @OneToMany(mappedBy = "taskList", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Link> links;

    public String toString() {
        return this.title;
    }
}
