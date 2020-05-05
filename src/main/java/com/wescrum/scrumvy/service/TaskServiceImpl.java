
package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Task;
import com.wescrum.scrumvy.repos.TaskRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskServiceInterface {
    
    @Autowired
    TaskRepository taskRepo;

    @Override
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    @Override
    public void createTask(Task task) {
        taskRepo.save(task);
    }

    @Override
    public Task getTaskbyid(Long id) {
        return taskRepo.getOne(id);
    }

    @Override
    public void deleteTask(Task task) {
        taskRepo.delete(task);
    }

    @Override
    public void updateTask(Task task) {
        taskRepo.save(task);
    }

    @Override
    public List<Task> findbyProjectId(Long projectId) {
        return taskRepo.findbyProjectId(projectId);
    }
    
}
