package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.ProjectRole;
import com.wescrum.scrumvy.entity.ProjectTeam;
import com.wescrum.scrumvy.entity.Task;
import com.wescrum.scrumvy.entity.User;
import com.wescrum.scrumvy.repos.ProjectRoleRepository;
import com.wescrum.scrumvy.repos.ProjectTeamRepository;
import com.wescrum.scrumvy.service.ProjectServiceInterface;
import com.wescrum.scrumvy.service.ProjectTeamServiceInterface;
import com.wescrum.scrumvy.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectServiceInterface projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectTeamServiceInterface projectTeamService;
    @Autowired
    private ProjectRoleRepository projectRoleRepo;
    @Autowired
    private ProjectTeamRepository projectTeamRepo;
    public static long activeProject;
    public static long activeUser;

    @GetMapping("/createProject")
    public String createProject(Model model, final RedirectAttributes redirectAttributes) {
        User user = userService.getLoggedinUser();
        activeUser = user.getId();

        ProjectRole projectRole = projectRoleRepo.findByprojectRoleId(1);
        List<ProjectTeam> usersOwnedProjects = projectTeamRepo.findByUserIdAndProjectRoleId(user, projectRole);

        if ((user.getPremium() == false) && (usersOwnedProjects.size() >= 1)) {
            redirectAttributes.addFlashAttribute("createProjectError", "I would love to create a new project for you. If you want me to do this Go premium!");
            return "redirect:/";
        } else {
            model.addAttribute("project", new Project());
            model.addAttribute("customUser", user);
            return "createProjectForm";
        }
    }

    @PostMapping("/saveProject")
    public String saveProject(@Valid @ModelAttribute("project") Project project,
            BindingResult theBindingResult,
            @ModelAttribute("userCollection") Long formUser,
            Model model) {
        if (theBindingResult.hasErrors()) {
            return "createProjectForm";
        }
        project = trimTheProject(project);
        User user = userService.getLoggedinUser();

        //we can apply some ban here
        if (formUser != activeUser) {
            model.addAttribute("createProjectError", "Please do not tamper with hidden form fields.");
            model.addAttribute("project", new Project());
            return "createProjectForm";
        }
        //unique name of project/user
        List<Project> tempList = projectService.getAllOwnedProjectsOfAUser(user.getId());
        boolean exists = false;
        for (Project pr : tempList) {
            if (pr.getProjectName().toLowerCase().equals(project.getProjectName().toLowerCase())) {
                exists = true;
            }
        }
        if (exists) {
            model.addAttribute("createProjectError", "Sorry. It seems you already have a project named that way");
            model.addAttribute("project", new Project());
            return "createProjectForm";
        }

        ProjectRole projectRole = projectRoleRepo.findByprojectRoleId(1);
        projectService.createProject(project);

        ProjectTeam projectTeam = new ProjectTeam();
        projectTeam.setProjectId(project);
        projectTeam.setUserId(user);
        projectTeam.setProjectRoleId(projectRole);
        projectTeamService.saveTeam(projectTeam);

        user.getProjectsCollection().add(project);
        userService.saveUserWithProject(user);

        return "redirect:/";
    }

    @PostMapping("/updateProjectDetails")
    public String updateProjectDetails(@Valid @ModelAttribute("project") Project project,
            BindingResult theBindingResult,
            @ModelAttribute("projectId") Long formProject,
            @ModelAttribute("userCollection") Long formUser,
            final RedirectAttributes redirectAttributes,
            Model model
    ) {
        project = trimTheProject(project);

        //we can apply some ban here
        if ((formProject != activeProject) && (formUser != activeUser)) {
            redirectAttributes.addFlashAttribute("createProjectError", "Please do not tamper with hidden form fields.");
            return "redirect:/";
        }

        // form validation
        if (theBindingResult.hasErrors()) {
            model.addAttribute("emptyTask", new Task());
            return "projectSetup";
        }

        // check if project is owned by this user
        if (!projectService.checkIdOfOwnedProjectsFix(project)) {
            model.addAttribute("createProjectError", "You do not own this project");
            model.addAttribute("emptyTask", new Task());
            return "projectSetup";
        }

        // unique name per project validation
        if (checkForSameName(project)) {
            model.addAttribute("createProjectError", "Sorry. It seems you already have a project named that way");
            model.addAttribute("emptyTask", new Task());
            return "projectSetup";
        }

        model.addAttribute("project", project);
        model.addAttribute("emptyTask", new Task());

        projectService.createProject(project);
        return "projectSetup";
    }

    @PostMapping("/projectSettings")
    public String projectSettings(@ModelAttribute("projectId") Long projectid,
            Model model,
            final RedirectAttributes redirectAttributes
    ) {
        Project project = projectService.getProjectbyid(projectid);
        activeProject = project.getProjectId();
        if (projectService.checkIdOfOwnedProjectsFix(project)) {
            model.addAttribute("user", userService.getLoggedinUser());
            model.addAttribute("project", project);
            Task task = new Task();
            task.setProjectId(projectService.getProjectbyid(activeProject));
            model.addAttribute("emptyTask", task);
            return "projectSetup";
        } else {
            redirectAttributes.addFlashAttribute("createProjectError", "The project you are trying to join is not yours.");
            return "redirect:/";
        }
    }

    @PostMapping("/deleteProject")
    public String deleteProject(@ModelAttribute("projectId") Long projectid,
            @ModelAttribute("projectId") Long formProject,
            final RedirectAttributes redirectAttributes,
            Model model
    ) {
        //we can apply some ban here
        if ((formProject != activeProject)) {
            redirectAttributes.addFlashAttribute("createProjectError", "Please do not tamper with hidden form fields.");
            return "redirect:/";
        }

        Project project = projectService.getProjectbyid(projectid);
        if (projectService.checkIfProjectIsOwned(project)) {
            projectService.deleteProject(projectService.getProjectbyid(projectid));
            return "redirect:/";
        } else {
            model.addAttribute("deleteProjectError", "The project you are trying to delete is not yours.");
            model.addAttribute(project);
            model.addAttribute("emptyTask", new Task());
            return "projectSetup";
        }
    }

    private Project trimTheProject(Project project) {
        String trimmedName = project.getProjectName().trim();
        String trimmedDescription = project.getProjectDescription().trim();
        project.setProjectName(trimmedName);
        project.setProjectDescription(trimmedDescription);
        return project;
    }

    private boolean checkForSameName(Project project) {
        List<Project> tempList = projectService.getAllOwnedProjectsOfAUser(userService.getLoggedinUser().getId());
        for (Project pr : tempList) {
            if ((pr.getProjectName().toLowerCase().equals(project.getProjectName().toLowerCase())) && (!Objects.equals(pr.getProjectId(), project.getProjectId()))) {
                return true;
            }
        }
        return false;
    }

}
