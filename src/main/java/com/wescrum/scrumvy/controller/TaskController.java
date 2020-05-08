package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.Status;
import com.wescrum.scrumvy.entity.Task;
import com.wescrum.scrumvy.repos.StatusRepository;
import com.wescrum.scrumvy.service.ProjectServiceInterface;
import com.wescrum.scrumvy.service.TaskServiceInterface;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ProjectServiceInterface projectService;
    @Autowired
    TaskServiceInterface taskService;
    @Autowired
    StatusRepository statusRepo;

    @PostMapping("/saveTask")
    public String saveTask(@Valid @ModelAttribute("emptyTask") Task task,
            BindingResult theBindingResult,
            Model model) {
        Project project = projectService.getProjectbyid(task.getProjectId().getProjectId());
        System.out.println(task.toString());

        // form validation
        if (theBindingResult.hasErrors()) {
            model.addAttribute("project", project);
            model.addAttribute("emptyTask", new Task());
            return "projectSetup";
        }

        Status status = statusRepo.findByStatusId(1);
        task.setStatusId(status);
        task.setProjectId(project);

        project.getTaskCollection().add(task);
        projectService.createProject(project);
        model.addAttribute("project", project);
        model.addAttribute("emptyTask", new Task());
        return "projectSetup";
    }

}
