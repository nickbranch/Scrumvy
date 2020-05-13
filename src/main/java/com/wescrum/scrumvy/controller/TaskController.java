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
        Project project = task.getProjectId();

        // form validation
        if (theBindingResult.hasErrors()) {
            model.addAttribute("project", project);
            model.addAttribute("emptyTask", new Task());
            return "projectSetup";
        }

        // if for any reason (front end error) status is null initialize it
        if (task.getStatusId() == null) {
            Status status = statusRepo.findByStatusId(1);
            task.setStatusId(status);
        }

        taskService.createTask(task);
        model.addAttribute("project", project);
        model.addAttribute("emptyTask", new Task());
        return "projectSetup";
    }

    @PostMapping("/deleteTask")
    public String deleteTask(@Valid @ModelAttribute("taskId") Long taskId,
            BindingResult theBindingResult,
            Model model) {
        Task task = taskService.getTaskbyid(taskId);
        Project project = task.getProjectId();

        // form validation
        if (theBindingResult.hasErrors()) {
            model.addAttribute("project", project);
            model.addAttribute("emptyTask", new Task());
            return "projectSetup";
        }
        taskService.deleteTask(task);
        model.addAttribute("project", project);
        model.addAttribute("emptyTask", new Task());
        return "projectSetup";
    }

    @PostMapping("/updateTask")
    public String updateTask(@Valid @ModelAttribute("emptyTask") Task task,
            BindingResult theBindingResult,
            Model model) {
        Project project = task.getProjectId();
        // form validation
        if (theBindingResult.hasErrors()) {
            model.addAttribute("project", project);
            model.addAttribute("emptyTask", new Task());
            return "projectSetup";
        }
        taskService.createTask(task);
        model.addAttribute("project", project);
        model.addAttribute("emptyTask", new Task());
        return "projectSetup";
    }
}
