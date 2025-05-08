package com.tasks.task.model.dto;

import org.springframework.http.HttpStatusCode;

import java.util.UUID;

public class Error {
    private UUID uuid;
    private String message;
    private HttpStatusCode code;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatusCode getCode() {
        return code;
    }

    public void setCode(HttpStatusCode code) {
        this.code = code;
    }

    public Error() {
    }

    public Error(UUID uuid, String message, HttpStatusCode code) {
        this.uuid = uuid;
        this.message = message;
        this.code = code;
    }
}
