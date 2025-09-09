package com.essjr.MeuGestorDeTarefas.models;


import com.essjr.MeuGestorDeTarefas.models.enuns.TaskPriority;
import com.essjr.MeuGestorDeTarefas.models.enuns.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_task")
public class Task {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Task() {
    }

    public Task(Long id, String title, String description, TaskStatus status, TaskPriority priority, LocalDateTime createdAt, LocalDateTime finishedAt, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
