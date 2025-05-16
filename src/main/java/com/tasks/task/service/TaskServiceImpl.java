package com.tasks.task.service;

import com.tasks.task.config.JwtUtil;
import com.tasks.task.model.Status;
import com.tasks.task.model.Task;
import com.tasks.task.model.dto.StatusRequest;
import com.tasks.task.model.dto.TaskRequest;
import com.tasks.task.repository.AuthRepository;
import com.tasks.task.repository.TaskRepository;
import com.tasks.task.service.exceptions.TaskNotFoundException;
import com.tasks.task.service.exceptions.IllegalArgumentException;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private JwtUtil jwtUtil;

//Recomendable es más facil hacer DI
//    @Autowired
//    public TaskServiceImpl(TaskRepository taskRepo) {
//        this.taskRepository=taskRepo;
//    }

    @Override
    public Task createTask(TaskRequest task){
        // Validación de datos de entrada
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (task.getDescription() == null || task.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        String username = authentication.getName();
        var user = authRepository.findByUsername(username);

        Task taskPersistence = new Task();
        taskPersistence.setDescription(task.getDescription());
        taskPersistence.setTitle(task.getTitle());
        taskPersistence.setStatus(Status.OPEN);
        taskPersistence.setUser(user);
        return taskRepository.save(taskPersistence);
    }

    @Override
    public Task deleteTask(UUID uuid) {
        Task task = taskRepository.findById(uuid)
                .orElseThrow(() -> new TaskNotFoundException("Task with UUID " + uuid + " not found"));
        taskRepository.delete(task);
        return task;
    }

    @Override
    public Task updateTask(UUID uuid, StatusRequest statusRequest) {
        // Validaciones
        if (statusRequest.getStatus() == null) {
            throw new IllegalArgumentException("Status cannot be empty");
        }

        Task taskPersistence = taskRepository.findById(uuid)
                .orElseThrow(() -> new TaskNotFoundException("Task with UUID " + uuid + " not found"));

        taskPersistence.setStatus(statusRequest.getStatus());
        return taskRepository.save(taskPersistence);
    }

    @Override
    public List<Task> getTaskStatus(Status status) {
        List<Task> tasks;

        if (status == null) {
            tasks = taskRepository.findAll();
        } else {
            tasks = taskRepository.findByStatus(status);
        }

        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Task with status " + status.name()+ " not found");
        }
        return tasks;
    }

    @Override
    public Task getTaskUuid(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }

        String uuidRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";
        if (!uuid.toString().matches(uuidRegex)) {
            throw new IllegalArgumentException("The UUID is not valid");
        }
        Task task = taskRepository.findByUuid(uuid);
        if (task == null) {
            throw new TaskNotFoundException("Task with UUID " + uuid + " not found");
        }

        return task;
    }

}
