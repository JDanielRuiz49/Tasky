package com.tasks.task.model.dto;

import com.tasks.task.model.Status;

public class BooleanStatus {
    private Boolean status;

    public BooleanStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
