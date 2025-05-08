package com.tasks.task.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity //Clase que tiene relación con la base de datos
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    private String title;
    private String description;
    private Status status;

    //cascadeType para guardar, eliminar, actualizar de una entidad a sus entidades relacionadas(ALL, PERSIST, REMOVE, MERGE)
    //fetch = FetchType para la carga de datos de la relación desde la BD(EAGER, LAZY)
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.EAGER)// verificar el atributo entidad
    @JsonManagedReference
    private List<CheckList> checklist = new ArrayList<>();

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
}
