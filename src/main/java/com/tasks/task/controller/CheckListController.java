package com.tasks.task.controller;

import com.tasks.task.model.CheckList;
import com.tasks.task.model.Task;
import com.tasks.task.model.dto.*;
import com.tasks.task.service.CheckListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/checkLists")
public class CheckListController {
    @Autowired
    private CheckListService checkListService;
//Investigar para que es validated y si lo podemos usar

    @Operation(
            summary = "Create Checklist",
            description = "Create a new Checklist",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Checklist to create",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CheckListRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Checklist created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CheckList.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid request",
                            content = @Content())
            }
    )

    @PostMapping("/{idTask}")
    public ResponseEntity<CheckListResponse>postCheckList(@Validated @RequestBody CheckListRequest checkListRequest, @PathVariable UUID idTask){
        CheckListResponse checkList = checkListService.createCheckList(checkListRequest,idTask);
        return ResponseEntity.status(HttpStatus.OK).body(checkList);
    }

    @Operation(
            summary = "Update checklist status",
            description = "Update the status of a checklist using its UUID",
            parameters = {
                    @Parameter(name = "id", in = ParameterIn.PATH, required = true, description = "UUID of the checklist")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Status update request",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "CheckedExample",
                                    summary = "Mark as done",
                                    value = """
                                    {
                                        "status": true
                                    }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task updated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = "", implementation = CheckList.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid status",
                            content = @Content()
                    ),
                    @ApiResponse(responseCode = "404", description = "checklist not found",
                            content = @Content()
                    ),
            }
    )
    @PatchMapping("/{id}")//CHECK
    public ResponseEntity<CheckListResponse>updateCheckList(@PathVariable UUID id, @RequestBody BooleanStatus booleanStatus) {
        CheckListResponse checkList = checkListService.updateCheckList(id, booleanStatus);
        return ResponseEntity.status(HttpStatus.OK).body(checkList);
    }
}
