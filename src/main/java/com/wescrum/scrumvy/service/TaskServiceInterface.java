package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Task;
import java.util.List;


public interface TaskServiceInterface {
    
    public List<Task> getAllTasks();

    public void createTask(Task task);

    public Task getTaskbyid(Long id);

    public void deleteTask(Task task);

    public void updateTask(Task task);
    
    public List<Task> findByProjectId (Long projectId);
 
}