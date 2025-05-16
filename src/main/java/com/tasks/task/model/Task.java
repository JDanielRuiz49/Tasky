package com.tasks.task.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity //Clase que tiene relación con la base de datos
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Unique identifier of the task", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID uuid;

    @Schema(description = "Title of the task", example = "first task")
    private String title;

    @Schema(description = "Description of the task", example = "description of the task")
    private String description;

    @Schema(description = "Current status of the task", example = "OPEN")
    private Status status;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference// Lado "padre" o propietario, se serializa
    private List<CheckList> checklist = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)//cargar la relación de inmediato, junto con la entidad principal
    @JsonManagedReference
    private User user;

    public Task() {
    }

    public Task(UUID uuid, String title, String description, Status status) {
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public List<CheckList> getChecklist() {
        return checklist;
    }

    public void setChecklist(List<CheckList> checklist) {
        this.checklist = checklist;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
