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
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @Autowired
    private UserService userService;
    
    

    @PostMapping("/createSprint")
    public String createSprint(@ModelAttribute("projectId") Long projectid,
            Model model) {

        Project project = projectService.getProjectbyid(projectid);

        model.addAttribute("activeTasks", getActiveTasks(project));

        model.addAttribute("sprint", new Sprint());

        model.addAttribute("project", project);

        return "createSprintForm";
    }
    
    

    @PostMapping("/saveSprint")
    public String saveSprint(@Valid @ModelAttribute("sprint") Sprint sprint,
            BindingResult theBindingResult,
            Model model) {

        Project project = projectService.getProjectbyid(sprint.getProjectId().getProjectId());

        Date sprintStartDate = sprint.getSprintStartDate();

        Date sprintEndDate = sprint.getSprintEndDate();

        Date projectStartDate = project.getStartDate();

        Date projectEndDate = project.getEndDate();

        if (projectStartDate != null || projectEndDate != null) {

            if ((sprintStartDate.compareTo(projectStartDate) < 0) || (sprintEndDate.compareTo(projectEndDate) > 0)) {

                model.addAttribute("activeTasks", getActiveTasks(project));

                model.addAttribute("createSprintError", "Make sure the dates are consistent with the current project schedule");

                model.addAttribute("sprint", new Sprint());

                model.addAttribute("project", project);

                return "createSprintForm";
            }
        }

        updateTaskWithSprintDates(sprint);

        sprintService.createSprint(sprint);

        model.addAttribute("project", project);

        List<Task> projectTasks = taskRepo.findByProjectId(project);
        model.addAttribute("projectTasks", projectTasks);

        List<Sprint> projectSprints = sprintRepo.findByProjectId(project);
        model.addAttribute("projectSprints", projectSprints);

        return "redirect:/project/projectDetails/" + project.getProjectId();
    }

    
    
    @PostMapping("/saveUpdatedSprint")
    public String updateSprint(@ModelAttribute("sprint") Sprint sprint,
            Model model) {

        Project project = projectService.getProjectbyid(sprint.getProjectId().getProjectId());

        Date sprintStartDate = sprint.getSprintStartDate();
        Date sprintEndDate = sprint.getSprintEndDate();
        Date projectStartDate = project.getStartDate();
        Date projectEndDate = project.getEndDate();
        System.out.println(sprintStartDate.toString());

        System.out.println(sprintEndDate.toString());

        if (projectStartDate != null || projectEndDate != null) {
            if ((sprintStartDate.compareTo(projectStartDate) < 0) && (sprintEndDate.compareTo(projectEndDate) > 0)) {

                //send again current tasks
                List<Task> currentTasks = new ArrayList();
                for (Task task : sprint.getTaskCollection()) {
                    currentTasks.add(task);
                }

                model.addAttribute("currentTasks", currentTasks);

                model.addAttribute("activeTasks", getActiveTasks(project));

                model.addAttribute("createSprintError", "Make sure the dates are consistent with the current project schedule");

                model.addAttribute("sprint", sprint);

                model.addAttribute("project", project);

                return "updateSprintForm";
            }
        }
        updateTaskWithSprintDates(sprint);

        sprintService.createSprint(sprint);

        model.addAttribute("project", project);

        List<Task> projectTasks = taskRepo.findByProjectId(project);
        model.addAttribute("projectTasks", projectTasks);

        List<Sprint> projectSprints = sprintRepo.findByProjectId(project);
        model.addAttribute("projectSprints", projectSprints);

        return "redirect:/project/projectDetails/" + project.getProjectId();

    }

    
    
    @PostMapping("/updateSprint")
    public String updateSprint(@ModelAttribute("sprintId") Long sprintId,
            Model model) {

        Sprint sprint = sprintService.getSprintbyid(sprintId);

        List<Task> currentTasks = new ArrayList();
        for (Task task : sprint.getTaskCollection()) {
            currentTasks.add(task);
        }

        Project project = sprint.getProjectId();

        model.addAttribute("activeTasks", getActiveTasks(project));

        model.addAttribute("currentTasks", currentTasks);

        model.addAttribute("project", project);

        model.addAttribute("sprint", sprint);

        return "updateSprintForm";
    }

    
    
    @PostMapping("/deleteSprint")
    public String deleteSprint(@ModelAttribute("sprintId") Long sprintId,
            Model model) {

        Sprint sprint = sprintService.getSprintbyid(sprintId);

        Project project = sprint.getProjectId();
//         if (checkIfProjectIsOwned(project)) {
//            sprintService.deleteSprint(sprint);
//            return "redirect:/";}
//        
        sprintService.deleteSprint(sprint);

        model.addAttribute("project", project);

        List<Task> projectTasks = taskRepo.findByProjectId(project);

        model.addAttribute("projectTasks", projectTasks);

        List<Sprint> projectSprints = sprintRepo.findByProjectId(project);

        model.addAttribute("projectSprints", projectSprints);

        return "redirect:/project/projectDetails/" + project.getProjectId();
    }

//    // METHODS : //
//    private boolean checkIfProjectIsOwned(Project project) {
//        List<Project> usersOwnedProjects = projectService.getAllOwnedProjectsOfAUser(userService.getLoggedinUser().getId());
//        if (usersOwnedProjects.contains(project)) {
//            return true;
//        }
//        return false;
//    }
    private void updateTaskWithSprintDates(Sprint sprint) {

        Date sprintStartDate = sprint.getSprintStartDate();
        Date sprintEndDate = sprint.getSprintEndDate();

        for (Task task : sprint.getTaskCollection()) {
            
            Task updateTask = taskService.getTaskbyid(task.getTaskId());
            
            updateTask.setTaskStartDate(sprintStartDate);
            
            updateTask.setTaskEndDate(sprintEndDate);
            
            taskService.createTask(updateTask);
        }
    }

    private List<Task> getActiveTasks(Project project) {

        List<Task> projectTasks = taskRepo.findByProjectId(project);

        List<Task> activeTasks = new ArrayList();

        for (Task task : projectTasks) {
            if (task.getStatusId().getStatusId() != 3) {
                activeTasks.add(task);
            }
        }
        return activeTasks;
    }

}
