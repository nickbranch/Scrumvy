package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.Sprint;
import com.wescrum.scrumvy.entity.Task;
import com.wescrum.scrumvy.repos.ProjectRepository;
import com.wescrum.scrumvy.repos.SprintRepository;
import com.wescrum.scrumvy.repos.TaskRepository;
import com.wescrum.scrumvy.service.ProjectServiceInterface;
import com.wescrum.scrumvy.service.SprintServiceInterface;
import com.wescrum.scrumvy.service.TaskServiceInterface;
import com.wescrum.scrumvy.service.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    // CREATE SPRINT
    @PostMapping("/createSprint")
    public String createSprint(@ModelAttribute("projectId") Long projectid,
            Model model,
            HttpServletRequest request,
            final RedirectAttributes redirectAttributes) {

        Project project = projectService.getProjectbyid(projectid);

        if(project.getProjectId()== request.getSession().getAttribute("activeProject")){  
            
        model.addAttribute("activeTasks", getActiveTasks(project));

        model.addAttribute("sprint", new Sprint());

        model.addAttribute("project", project);

        return "createSprintForm";
        } 
        
         redirectAttributes.addFlashAttribute("editTaskError", "Please do not tamper with hidden form fields.");
         return "redirect:/project/projectDetails/" + request.getSession().getAttribute("activeProject");
    }
    
    
    // CREATE BUTTON IN CREATE SPRINT FORM
    @PostMapping("/saveSprint")
    public String saveSprint(@Valid @ModelAttribute("sprint") Sprint sprint,
            BindingResult theBindingResult,
            Model model,
            HttpServletRequest request,
            final RedirectAttributes redirectAttributes) {
        
        Project project = projectService.getProjectbyid(sprint.getProjectId().getProjectId());
        
        Long currentProjectId = (Long)request.getSession().getAttribute("activeProject");
        Project currentproject = projectService.getProjectbyid(currentProjectId);
       
        // check if sprint belongs to the active Project
        if(project.getProjectId()== request.getSession().getAttribute("activeProject")){

        Date sprintStartDate = sprint.getSprintStartDate();

        Date sprintEndDate = sprint.getSprintEndDate();

        Date projectStartDate = currentproject.getStartDate();

        Date projectEndDate = currentproject.getEndDate();

         // check if sprint already exists with these dates:
         List <Sprint> projectSprints = sprintRepo.findByProjectId(currentproject);
         
         
         for(Sprint sprintCheck : projectSprints){
             System.out.println("lalalalalalallalalala");
             //doesnt work -  null pointer exception whith empty dates
             if (sprintCheck.getSprintStartDate().compareTo(sprintStartDate)== 0 || sprintCheck.getSprintEndDate().compareTo(sprintEndDate)==0){
            System.out.println("TATATATATATATATATATATATATATATATA");
             model.addAttribute("createSprintError", "You already have a sprint with these dates");
             model.addAttribute("activeTasks", getActiveTasks(currentproject));
             model.addAttribute("sprint", new Sprint());
             model.addAttribute("project", currentproject);
             return "createSprintForm";
         }
         }
        
        
        if (projectStartDate != null || projectEndDate != null) {

            if ((sprintStartDate.compareTo(projectStartDate) < 0) && (sprintEndDate.compareTo(projectEndDate) > 0)) {

                model.addAttribute("activeTasks", getActiveTasks(currentproject));

                model.addAttribute("createSprintError", "Make sure the dates are consistent with the current project schedule");

                model.addAttribute("sprint", new Sprint());

                model.addAttribute("project", currentproject);

                return "createSprintForm";
            }
        }

        updateTaskWithSprintDates(sprint);

        sprintService.createSprint(sprint);

        model.addAttribute("project", currentproject);

        List<Task> projectTasks = taskRepo.findByProjectId(currentproject);
        model.addAttribute("projectTasks", projectTasks);

//        List<Sprint> projectSprints = sprintRepo.findByProjectId(project);
        model.addAttribute("projectSprints", projectSprints);
        
//        redirectAttributes.addFlashAttribute("projectId", project.getProjectId());
        return "redirect:/project/projectDetails/" + currentProjectId;
        }       
    
        redirectAttributes.addFlashAttribute("editTaskError", "Please do not tamper with hidden form fields.");
         return "redirect:/project/projectDetails/" + currentProjectId;
    }

    
    // UPDATE BUTTON IN UPDATE SPRINT FORM
    @PostMapping("/saveUpdatedSprint")
    public String updateSprint(@ModelAttribute("sprint") Sprint sprint,
            Model model,
            HttpServletRequest request,
            final RedirectAttributes redirectAttributes) {

        Project project = projectService.getProjectbyid(sprint.getProjectId().getProjectId());
        
        Long currentProjectId = (Long)request.getSession().getAttribute("activeProject");
        Project currentproject = projectService.getProjectbyid(currentProjectId);
        
        if(project.getProjectId()== request.getSession().getAttribute("activeProject") && (sprintRepo.findByProjectIdAndSprintId(currentproject, sprint.getSprintId())).isPresent()){
        
        
        Date sprintStartDate = sprint.getSprintStartDate();
        Date sprintEndDate = sprint.getSprintEndDate();
        Date projectStartDate = currentproject.getStartDate();
        Date projectEndDate = currentproject.getEndDate();
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

                model.addAttribute("activeTasks", getActiveTasks(currentproject));

                model.addAttribute("createSprintError", "Make sure the dates are consistent with the current project schedule");

                model.addAttribute("sprint", sprint);

                model.addAttribute("project", currentproject);

                return "updateSprintForm";
            }
        }
        updateTaskWithSprintDates(sprint);

        sprintService.createSprint(sprint);

        model.addAttribute("project", currentproject);

        List<Task> projectTasks = taskRepo.findByProjectId(currentproject);
        model.addAttribute("projectTasks", projectTasks);

        List<Sprint> projectSprints = sprintRepo.findByProjectId(currentproject);
        model.addAttribute("projectSprints", projectSprints);

        return "redirect:/project/projectDetails/" + currentProjectId;
        }
        
         List<Task> currentTasks = new ArrayList();
                for (Task task : sprint.getTaskCollection()) {
                    currentTasks.add(task);
                }

                model.addAttribute("currentTasks", currentTasks);

                model.addAttribute("activeTasks", getActiveTasks(currentproject));

                model.addAttribute("createSprintError", "Please do not tamper with hidden form fields.");

                model.addAttribute("sprint", sprint);

                model.addAttribute("project", currentproject);

                return "updateSprintForm";
    }

    
    
    @PostMapping("/updateSprint")
    public String updateSprint(@ModelAttribute("sprintId") Long sprintId,
            Model model,
            HttpServletRequest request,
            final RedirectAttributes redirectAttributes) {

        Sprint sprint = sprintService.getSprintbyid(sprintId);
          
        // check if sprint belongs to the project:
        Long currentProjectId = (Long)request.getSession().getAttribute("activeProject");
        Project currentproject = projectService.getProjectbyid(currentProjectId);
        if ((sprintRepo.findByProjectIdAndSprintId(currentproject, sprintId)).isPresent()){

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
        
        } else {
             
         redirectAttributes.addFlashAttribute("editTaskError", "Please do not tamper with hidden form fields.");
         
         return "redirect:/project/projectDetails/" + request.getSession().getAttribute("activeProject");
    }
    }
    
    
    @PostMapping("/deleteSprint")
    public String deleteSprint(@ModelAttribute("sprintId") Long sprintId,
            Model model,
            HttpServletRequest request,
            final RedirectAttributes redirectAttributes) {

        Sprint sprint = sprintService.getSprintbyid(sprintId);
         Long currentProjectId = (Long)request.getSession().getAttribute("activeProject");
         Project currentproject = projectService.getProjectbyid(currentProjectId);
        if ((sprintRepo.findByProjectIdAndSprintId(currentproject, sprintId)).isPresent()){
           
        sprintService.deleteSprint(sprint);

        model.addAttribute("project", currentproject);

        List<Task> projectTasks = taskRepo.findByProjectId(currentproject);

        model.addAttribute("projectTasks", projectTasks);

        List<Sprint> projectSprints = sprintRepo.findByProjectId(currentproject);

        model.addAttribute("projectSprints", projectSprints);

        return "redirect:/project/projectDetails/" + currentProjectId;
    }   
        else {
            
         redirectAttributes.addFlashAttribute("editTaskError", "Please do not tamper with hidden form fields.");
         return "redirect:/project/projectDetails/" + currentProjectId;
    }
    
}

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
