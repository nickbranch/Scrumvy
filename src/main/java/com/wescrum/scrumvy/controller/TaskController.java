package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.Status;
import com.wescrum.scrumvy.entity.Task;
import com.wescrum.scrumvy.repos.StatusRepository;
import com.wescrum.scrumvy.repos.TaskRepository;
import com.wescrum.scrumvy.service.ProjectServiceInterface;
import com.wescrum.scrumvy.service.TaskServiceInterface;
import javax.servlet.http.HttpServletRequest;
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
            HttpServletRequest request,
            Model model) {

        //we can apply some ban here
        if (formProject != request.getSession().getAttribute("activeProject")) {
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

        // we want the status to be (todo) on the iniatialization also protects against form field tampering
        Status status = statusRepo.findByStatusId(1);
        task.setStatusId(status);

        taskService.createTask(task);
        model.addAttribute("project", project);
        model.addAttribute("emptyTask", new Task());
        return "projectSetup";
    }

    @PostMapping("/deleteTask")
    public String deleteTask(@Valid @ModelAttribute("taskId") Long taskId,
            BindingResult theBindingResult,
            @ModelAttribute("taskId") Long formTask,
            final RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            Model model) {
        Long pid = (Long) request.getSession().getAttribute("activeProject");
        Project project = projectService.getProjectbyid(pid);
 
        //form validation
        if (theBindingResult.hasErrors()) {
            model.addAttribute("project", project);
            model.addAttribute("emptyTask", new Task());
            return "projectSetup";
        }
        
        Task task = taskService.getTaskbyid(taskId);

        if (taskService.checkIfProjectOwnsATask(task, project)) {
            taskService.deleteTask(task);
            model.addAttribute("project", project);
            System.out.println("******************************************WE ARE IN6");
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
            @ModelAttribute("projectId") Long formProject,
            final RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            Model model) {
        Long pid = (Long) request.getSession().getAttribute("activeProject");
        Project project = projectService.getProjectbyid(pid);

        //we can apply some ban here
        if (formProject != pid) {
            redirectAttributes.addFlashAttribute("createProjectError", "Please do not tamper with hidden form fields.");
            return "redirect:/";
        }
        // form validation
        if (theBindingResult.hasErrors()) {
            model.addAttribute("project", project);
            model.addAttribute("emptyTask", new Task());
            return "projectSetup";
        }
        if (taskService.checkIfProjectOwnsATask(task, project)) {
            taskService.createTask(task);
            model.addAttribute("project", project);
            model.addAttribute("emptyTask", new Task());
            return "projectSetup";
        } else {
            redirectAttributes.addFlashAttribute("createProjectError", "Please do not tamper with hidden form fields.");
            return "redirect:/";
        }
    }
}
