package com.tasks.task.service;

import com.tasks.task.model.dto.BooleanStatus;
import com.tasks.task.model.dto.CheckListRequest;
import com.tasks.task.model.dto.CheckListResponse;

import java.util.UUID;

public interface CheckListService {
    CheckListResponse createCheckList(CheckListRequest checkListRequest, UUID idTask);
    CheckListResponse updateCheckList(UUID idCheckList, BooleanStatus statusRequest);

}
