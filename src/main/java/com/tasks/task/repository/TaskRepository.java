package com.tasks.task.repository;

import com.tasks.task.model.Status;
import com.tasks.task.model.Task;
import com.tasks.task.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByStatusAndUser(Status status, User user);
    List<Task> findByUser(User user);
    Task findByUuid(UUID uuid);
}
