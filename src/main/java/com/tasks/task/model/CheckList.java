package com.tasks.task.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;
@Entity
public class CheckList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "taskId", referencedColumnName = "uuid")//Verificar el referencedColumnName
    @JsonBackReference
//    @JsonIgnore
    private Task task;

//    public CheckList(String description, boolean status) {
//        this.description = description;
//        this.status = status;
//    }
//
//    public CheckList() {
//    }


    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
