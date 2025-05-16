package com.tasks.task.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CheckListRequest{

    @Schema(description = "Description Checklist", example = "first checklist item")
    private String description;

    @Schema(description = "Status of the Checklist", example = "Checklist item status")
    private boolean status;

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
