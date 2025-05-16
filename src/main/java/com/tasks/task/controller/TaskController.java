package com.tasks.task.controller;

import com.tasks.task.model.Status;
import com.tasks.task.model.Task;
import com.tasks.task.model.dto.StatusRequest;
import com.tasks.task.model.dto.TaskRequest;
import com.tasks.task.service.TaskServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Controlador REST que gestiona operaciones relacionadas con las tareas (Task).
 * Proporciona endpoints para crear, obtener, actualizar y eliminar tareas.
 */
@Tag(name ="task", description = "Everything about your tasks")


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/tasks")
@SuppressWarnings("unused")
public class TaskController {

    private final TaskServiceImpl taskService;

    //spring automáticamente usa el constructor asi que no necesitas inyectar dependencia a través del campo
    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    /**
     * Crea una nueva tarea con la información proporcionada en el cuerpo de la solicitud.
     *
     * @param taskRequest Objeto que contiene los datos de la tarea a crear.
     * @return ResponseEntity con mensaje de éxito y la tarea creada.
     */

    @Operation(
            summary = "Create task",
            description = "Create a new task",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Task to create",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaskRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Task.class)
                        )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content())
            }
    )

    @PostMapping()//CHECK
    public ResponseEntity<Map<String, Object>> createTask(@RequestBody TaskRequest taskRequest) {//Respuesta de entidad código de error, o json
        //dto: data transfer object
        //obj response: body, obj request:solicitud
        //[] significa que está devolviendo un arreglo
        //{} significa que está devolviendo un obj
        Task task = taskService.createTask(taskRequest);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Successful operation");
        response.put("data", task);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    /**
     * Recupera una tarea específica utilizando su identificador UUID.
     *
     * @param id UUID de la tarea a recuperar.
     * @return ResponseEntity con mensaje de éxito y la tarea encontrada.
     */

    @Operation(
            summary = "Get task by UUID",
            description = "Retrieve a specific task by its UUID",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.PATH, description = "UUID of the task")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Task.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid UUID format",
                            content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Task not found",
                            content = @Content())
            }
    )

    @GetMapping("/{id}")//CHECK
    public ResponseEntity<Map<String, Object>> getTaskByUuid(
            @PathVariable String id
    ) {
        Map<String, Object> response = new HashMap<>();

        if (id == null || id.trim().isEmpty()) {
            response.put("message", "No UUID provided");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            UUID uuid = UUID.fromString(id);
            Task task = taskService.getTaskUuid(uuid);
            response.put("message", "Successful operation");
            response.put("data", task);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            response.put("message", "Invalid UUID format");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Recupera todas las tareas que tienen un estado específico.
     *
     * @param status Estado de las tareas a recuperar (ej. PENDING, DONE).
     * @return ResponseEntity con lista de tareas filtradas por estado.
     */

    @Operation(
            summary = "Get list of tasks",
            parameters = {
                    @Parameter(
                            name = "status",
                            in = ParameterIn.QUERY,
                            description = "Filter tasks by status (OPEN, IN_PROGRESS, DONE). Leave empty to get all.",
                            schema = @Schema(type = "string", allowableValues = {"OPEN", "IN_PROGRESS", "DONE"})
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of tasks",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = "", implementation = Task.class),
                                    examples = @ExampleObject(
                                            value = """ 
                                                    [
                                                       {
                                                          "uuid": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                                                          "title": "Title of the task",
                                                          "description": "Description of the task",
                                                          "status": "OPEN",
                                                          "checklist": [],
                                                          "user": {}
                                                       },
                                                       {
                                                          "uuid": "4fa85f64-5717-4562-b3fc-2c963f66afb7",
                                                          "title": "Another task",
                                                          "description": "Another description",
                                                          "status": "IN_PROGRESS",
                                                          "checklist": [],
                                                          "user": {}
                                                       }
                                                    ]
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Task with this status not found",
                            content = @Content()),
                    @ApiResponse(responseCode = "500", description = "Server error",
                            content = @Content())
            }
    )

    //query params
    @GetMapping("")//CHECK
    public ResponseEntity<Map<String, Object>> getTasksByStatus(@RequestParam(required = false) Status status) {
        List<Task> tasks = taskService.getTaskStatus(status);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "List of tasks");
        response.put("data", tasks);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Elimina una tarea específica utilizando su UUID.
     *
     * @param id UUID de la tarea a eliminar.
     * @return ResponseEntity con mensaje de confirmación y la tarea eliminada.
     */

    @Operation(
            summary = "Delete task",
            description = "Delete a specific task by UUID",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.PATH, required = true, description = "UUID of the task")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task deleted",
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(type = "", implementation = Task.class)
                        )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid UUID format",
                        content = @Content()
                    ),
                    @ApiResponse(responseCode = "404", description = "Task not found",
                        content = @Content()
                    )
            }
    )
    @DeleteMapping("/{id}")//CHECK
    public ResponseEntity<Map<String, Object>> deleteTasksByUuid(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        if (id == null || id.trim().isEmpty()) {
            response.put("message", "No UUID provided");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            UUID uuid = UUID.fromString(id);
            Task task = taskService.deleteTask(uuid);
            response.put("message", "The task was deleted");
            response.put("data", task);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            response.put("message", "Invalid UUID format");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Actualiza el estado de una tarea específica usando su UUID.
     *
     * @param id UUID de la tarea a actualizar.
     * @param statusRequest Objeto que contiene el nuevo estado de la tarea.
     * @return ResponseEntity con mensaje de éxito y la tarea actualizada.
     */

    @Operation(
            summary = "Update task status",
            description = "Update the status of a task using its UUID",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.PATH, required = true, description = "UUID of the task")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Status update request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StatusRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = "", implementation = Task.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid status",
                            content = @Content()
                    ),
                    @ApiResponse(responseCode = "404", description = "Task not found",
                            content = @Content()
                    ),
            }
    )
    @PatchMapping("/{id}")//CHECK
    public ResponseEntity<Map<String, Object>> updateTaskByUuid(@PathVariable String id, @RequestBody StatusRequest statusRequest) {
        Map<String, Object> response = new HashMap<>();
        if (id == null || id.trim().isEmpty()) {
            response.put("message", "No UUID provided");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            UUID uuid = UUID.fromString(id);
            Task task = taskService.updateTask(uuid, statusRequest);
            response.put("message", "The task was update successful");
            response.put("data", task);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalArgumentException e) {
            response.put("message", "Invalid UUID format");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
