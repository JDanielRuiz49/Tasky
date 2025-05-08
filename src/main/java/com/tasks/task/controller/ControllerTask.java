package com.tasks.task.controller;

import com.tasks.task.model.Status;
import com.tasks.task.model.Task;
import com.tasks.task.model.dto.StatusRequest;
import com.tasks.task.model.dto.TaskRequest;
import com.tasks.task.service.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/tasks")
public class ControllerTask {

    @Autowired
    private TaskServiceImpl taskService;

    @PostMapping()//CHECK
    public ResponseEntity<Map<String, Object>> createTask(@RequestBody TaskRequest taskRequest) {//Respuesta de entidad codigo de error, o json
        //dto: data transfer object
        //obj response: body, obj request:solicitud
        //[] significa que esta devolviendo un arreglo
        //{} significa que esta devolviendo un obj
        Task task = taskService.createTask(taskRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Successful operation");
        response.put("data", task);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")//CHECK
    public ResponseEntity<Map<String, Object>> getTaskByUuid(@PathVariable UUID id) {
        Task task = taskService.getTaskUuid(id);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Successful operation");
        response.put("data", task);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //queryparams
    @GetMapping("")//CHECK
    public ResponseEntity<Map<String, Object>> getTasksByStatus(@RequestParam Status status) {
        List<Task> tasks = taskService.getTaskStatus(status);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "List of tasks");
        response.put("data", tasks);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    //getMapping

    @DeleteMapping("/{id}")//CHECK
    public ResponseEntity<Map<String, Object>> deleteTasksByUuid(@PathVariable UUID id) {
        Task task = taskService.deleteTask(id);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "The task was deleted");
        response.put("data", task);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{id}")//CHECK
    public ResponseEntity<Map<String, Object>> updateTaskByUuid(@PathVariable UUID id, @RequestBody StatusRequest statusRequest) {
        Task task = taskService.updateTask(id, statusRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "The task was update successful");
        response.put("data", task);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //anotaciones
}
