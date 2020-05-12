/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wescrum.scrumvy.controller.restController;

import com.wescrum.scrumvy.entity.Sprint;
import com.wescrum.scrumvy.entity.Task;
import com.wescrum.scrumvy.pojo.TasksJsonResponse;
import com.wescrum.scrumvy.service.SprintServiceInterface;
import com.wescrum.scrumvy.service.TaskServiceInterface;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkspaceController {
    
    @Autowired
    TaskServiceInterface taskService;
    
    @Autowired
    SprintServiceInterface sprintService;
    
    
    
    @PostMapping("/saveTask")
    public String saveTask(@ModelAttribute("task") Task task ) {
         
           System.out.println("##########################################");
         Task editedTask = taskService.getTaskbyid(task.getTaskId());
         editedTask.setDescription(task.getDescription());
         taskService.updateTask(editedTask);
         System.out.println(editedTask.getSprintCollection());
//         return editedTask;
         return "hi";
    }
    

    
    @PostMapping ("/getSprintTasks")
    public List <TasksJsonResponse> getSprintTasks(@ModelAttribute("sprintId") Long sprintId) {
        
        Sprint sprint = sprintService.getSprintbyid(sprintId);
        
        List <TasksJsonResponse> listOfPojoTasks = new ArrayList();
        
        List <Task> sprintTasks = (List) sprint.getTaskCollection();
        
        for (Task task : sprintTasks){
            TasksJsonResponse pojoTask = new TasksJsonResponse(task.getTaskId(),task.getDescription(),task.getStatusId().getStatusId());
            listOfPojoTasks.add(pojoTask);
        }
        
        return listOfPojoTasks;
    }
   
}
