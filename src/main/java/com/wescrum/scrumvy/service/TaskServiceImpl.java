package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.Task;
import com.wescrum.scrumvy.repos.TaskRepository;
import java.util.List;
import java.util.Objects;
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
    public boolean checkIfProjectOwnsATask(Task task, Project project) {
        List<Task> listOfTasks = taskRepo.findByProjectId(project);
        boolean toggle = false;
        for (Task prTask : listOfTasks) {
            if (Objects.equals(prTask.getTaskId(), task.getTaskId())) {
                toggle = true;
            }
        }
        return toggle;
    }

}
