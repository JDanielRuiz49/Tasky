package com.tasks.task.model.dto;

import com.tasks.task.model.Status;

import java.util.UUID;

public class StatusRequest {
    private Status status;

    public StatusRequest(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
