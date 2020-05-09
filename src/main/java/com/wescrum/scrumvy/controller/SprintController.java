/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.Sprint;
import com.wescrum.scrumvy.entity.Task;
import com.wescrum.scrumvy.repos.SprintRepository;

import com.wescrum.scrumvy.repos.TaskRepository;

import com.wescrum.scrumvy.service.ProjectServiceInterface;
import com.wescrum.scrumvy.service.SprintServiceInterface;
import com.wescrum.scrumvy.service.TaskServiceInterface;
import com.wescrum.scrumvy.service.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sprint")
public class SprintController {

    @Autowired
    private ProjectServiceInterface projectService;

    @Autowired
    private TaskRepository taskRepo;
    
    @Autowired
    private TaskServiceInterface taskService;

    @Autowired
    private SprintServiceInterface sprintService;

    @Autowired
    private SprintRepository sprintRepo;

    @PostMapping("/createSprint")
    public String createSprint(@ModelAttribute("projectId") Long projectid,
            Model model) {

        Project project = projectService.getProjectbyid(projectid);
        List<Task> projectTasks = taskRepo.findByProjectId(project);
        // confirmation - na travaei osa status != complete
        List<Task> activeTasks = new ArrayList();
        
        for (Task task : projectTasks) {
            if(task.getStatusId().getStatusId() != 1) {
            activeTasks.add(task);
                }
        }
       
        model.addAttribute ("activeTasks", activeTasks);
        model.addAttribute("taskList", projectTasks);
        model.addAttribute("sprint", new Sprint());
        model.addAttribute("project", project);
        
        return "createSprintForm";
    }

    @PostMapping("/saveSprint")
    public String saveSprint(@ModelAttribute("sprint") Sprint sprint,
            Model model) {

        System.out.println("**********************");
        System.out.println(sprint.getTaskCollection().toString());

        Project project = projectService.getProjectbyid(sprint.getProjectId().getProjectId());
        sprint.setProjectId(project);

        project.getSprintCollection().add(sprint);

        Date sprintStartDate = sprint.getSprintStartDate();
        Date sprintEndDate = sprint.getSprintEndDate();
        Date projectStartDate = project.getStartDate();
        Date projectEndDate = project.getEndDate();

        if ((sprintStartDate.compareTo(projectStartDate) < 0) || (sprintEndDate.compareTo(projectEndDate) > 0)) { 
            model.addAttribute("createSprintError", "Make sure the dates are consistent with the current project schedule");
            model.addAttribute("sprint", sprint);
            return "createSprintForm";
        }
        
        // update selected task dates
         for (Task task: sprint.getTaskCollection()){
            Task updateTask = taskService.getTaskbyid(task.getTaskId());
            updateTask.setTaskStartDate(sprintStartDate);
            updateTask.setTaskEndDate(sprintEndDate);
            taskService.createTask(updateTask);
        }
         
        sprintService.createSprint(sprint);
        
        model.addAttribute("project", project);
        List<Task> projectTasks = taskRepo.findByProjectId(project);
        model.addAttribute("projectTasks", projectTasks);
        List<Sprint> projectSprints = sprintRepo.findByProjectId(project);
        model.addAttribute("projectSprints", projectSprints);

        return "redirect:/project/projectDetails/" + project.getProjectId();
    }

}
