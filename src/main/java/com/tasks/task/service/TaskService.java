package com.tasks.task.service;

import com.tasks.task.model.Status;
import com.tasks.task.model.Task;
import com.tasks.task.model.dto.StatusRequest;
import com.tasks.task.model.dto.TaskRequest;

import java.util.List;
import java.util.UUID;

public interface TaskService {
//Firma del metodo no puede ser la misma entidad, no es lo ideal
    Task createTask(TaskRequest task);
    Task deleteTask(UUID uuid);
    Task updateTask(UUID uuid, StatusRequest statusRequest);
    List<Task> getTaskStatus(Status status);
    Task getTaskUuid(UUID uuid);
}
