package com.tasks.task.repository;

import com.tasks.task.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String userName);
    User findByUsername (String userName);


}
