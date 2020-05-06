package com.wescrum.scrumvy.repos;


import com.wescrum.scrumvy.entity.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    public List<Task> findByProjectId(Long projectId);
    
}