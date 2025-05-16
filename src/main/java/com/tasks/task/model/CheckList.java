package com.tasks.task.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.UUID;
@Entity
public class CheckList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Unique identifier of the checklist item", example = "7b4e9c9f-b7f6-4b42-bc5f-8b4eaa877723")
    private UUID id;

    @Schema(description = "Description of the checklist item", example = "Description of the checklist item")
    private String description;

    @Schema(description = "Status of the checklist item", example = "false")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "taskId", referencedColumnName = "uuid")//Verificar el referencedColumnName
    @JsonBackReference
    @Schema(hidden = true)
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
