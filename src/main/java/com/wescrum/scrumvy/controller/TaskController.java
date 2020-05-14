package com.wescrum.scrumvy.controller;

import static com.wescrum.scrumvy.controller.ProjectController.activeUser;
import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.Status;
import com.wescrum.scrumvy.entity.Task;
import com.wescrum.scrumvy.repos.StatusRepository;
import com.wescrum.scrumvy.repos.TaskRepository;
import com.wescrum.scrumvy.service.ProjectServiceInterface;
import com.wescrum.scrumvy.service.TaskServiceInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ProjectServiceInterface projectService;
    @Autowired
    TaskServiceInterface taskService;
    @Autowired
    TaskRepository taskRepo;
    @Autowired
    private StatusRepository statusRepo;

    @PostMapping("/saveTask")
    public String saveTask(@Valid @ModelAttribute("emptyTask") Task task,
            BindingResult theBindingResult,
            @ModelAttribute("projectId") Long formProject,
            final RedirectAttributes redirectAttributes,
            Model model) {

        //we can apply some ban here
        if (formProject != ProjectController.activeProject) {
            redirectAttributes.addFlashAttribute("createProjectError", "Please do not tamper with hidden form fields.");
            return "redirect:/";
        }
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
            final RedirectAttributes redirectAttributes,
            Model model) {
        // form validation
        Project project = projectService.getProjectbyid(ProjectController.activeProject);
        if (theBindingResult.hasErrors()) {
            model.addAttribute("project", project);
            model.addAttribute("emptyTask", new Task());
            return "projectSetup";
        }
        Task task = taskService.getTaskbyid(taskId);
        List<Task> listOfTasks = taskRepo.findByProjectId(project);
        boolean toggle = false;
        for (Task prTask : listOfTasks) {
            if (Objects.equals(prTask.getTaskId(), task.getTaskId())) {
                toggle = true;
            }
        }
//        System.out.println("*****************************************" + listOfTasks);
        if (toggle) {
            taskService.deleteTask(task);
            model.addAttribute("project", project);
            model.addAttribute("emptyTask", new Task());
            return "projectSetup";
        } else {
            redirectAttributes.addFlashAttribute("createProjectError", "Please do not tamper with hidden form fields.");
            return "redirect:/";
        }
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
