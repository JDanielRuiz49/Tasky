package com.tasks.task.model.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskRequest {
    @Schema(description = "Title of the task", example = "first task")
    private String title;

    @Schema(description = "Detailed description of the task", example = "description of the task")
    private String description;

    public TaskRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public TaskRequest() {
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
}
