package com.tasks.task.service;

import com.tasks.task.model.CheckList;
import com.tasks.task.model.Task;
import com.tasks.task.model.dto.BooleanStatus;
import com.tasks.task.model.dto.CheckListRequest;
import com.tasks.task.model.dto.CheckListResponse;
import com.tasks.task.repository.CheckListRepository;
import com.tasks.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service //Revisar las anotaciones importantes de Spring
public class CheckListServiceImpl implements CheckListService{
    @Autowired
    private CheckListRepository checkListRepository;//Inyección por atributo de clase
    @Autowired
    private TaskRepository taskRepository;//Inyeccióm por atributo de clase (Autowired)

    @Override
    public CheckListResponse createCheckList(CheckListRequest checkListRequest, UUID idTask) {

        CheckList checkListPersistence= new CheckList();
        checkListPersistence.setDescription(checkListRequest.getDescription());
        checkListPersistence.setStatus(checkListRequest.isStatus());

        Task task = taskRepository.findByUuid(idTask);
        checkListPersistence.setTask(task);
        CheckList checkList = checkListRepository.save(checkListPersistence);//recibe una entidad de objeto que va a persistir


        CheckListResponse checkListResponse = new CheckListResponse();
        checkListResponse.setId(checkList.getId());
        checkListResponse.setDescription(checkList.getDescription());
        checkListResponse.setStatus(checkList.isStatus());

        return checkListResponse;
    }

    @Override
    public CheckListResponse updateCheckList(UUID idCheckList, BooleanStatus status) {

        String uuidRegex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";
        if (!idCheckList.toString().matches(uuidRegex)) {
            throw new IllegalArgumentException("The UUID is not valid");
        }
        CheckList checkList = checkListRepository.findById(idCheckList)
                .orElseThrow(() -> new IllegalArgumentException("Checklist with UUID " + idCheckList + " not found"));
        checkList.setStatus(status.getStatus());
        checkListRepository.save(checkList);

        CheckListResponse response = new CheckListResponse();
        response.setId(checkList.getId());
        response.setDescription(checkList.getDescription());
        response.setStatus(checkList.isStatus());


        return response;
    }
}
