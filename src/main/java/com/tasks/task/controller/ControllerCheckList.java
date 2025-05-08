package com.tasks.task.controller;

import com.tasks.task.model.dto.BooleanStatus;
import com.tasks.task.model.dto.CheckListRequest;
import com.tasks.task.model.dto.CheckListResponse;
import com.tasks.task.service.CheckListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/checkLists")
public class ControllerCheckList {
    @Autowired
    private CheckListService checkListService;
//Investigar para que es validated y si lo podemos usar

    @PostMapping("/{idTask}")
    public ResponseEntity<CheckListResponse>postCheckList(@Validated @RequestBody CheckListRequest checkListRequest, @PathVariable UUID idTask){
        CheckListResponse checkList = checkListService.createCheckList(checkListRequest,idTask);
        return ResponseEntity.status(HttpStatus.OK).body(checkList);
    }

    @PatchMapping("/{id}")//CHECK
    public ResponseEntity<CheckListResponse>updateCheckList(@PathVariable UUID id, @RequestBody BooleanStatus booleanStatus) {
        CheckListResponse checkList = checkListService.updateCheckList(id, booleanStatus);
        return ResponseEntity.status(HttpStatus.OK).body(checkList);
    }
}
