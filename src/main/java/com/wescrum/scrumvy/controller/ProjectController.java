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
    ProjectTeamRepository projectTeamRepo;

    @GetMapping("/createProject")
    public String createProject(Model model) {
        User user = userService.getLoggedinUser();

        ProjectRole projectRole = projectRoleRepo.findByprojectRoleId(1);
        List<ProjectTeam> usersOwnedProjects = projectTeamRepo.findByUserIdAndProjectRoleId(user, projectRole);

        int numberOfProjects = usersOwnedProjects.size();
        if ((user.getPremium() == false) && (numberOfProjects >= 1)) {
            model.addAttribute("createProjectError", "I would love to create a new project for you. If you want me to do this Go premium!");
            return "forward:/";
        } else {
            model.addAttribute("project", new Project());
            model.addAttribute("customUser", user);
            return "createProjectForm";
        }
    }

    @PostMapping("/saveProject")
    public String saveProject(@Valid @ModelAttribute("project") Project project,
            BindingResult theBindingResult,
            Model model) {
        project = trimTheProject(project);
        User user = userService.getLoggedinUser();

        // form validation
        if (theBindingResult.hasErrors()) {
            return "createProjectForm";
        }

        List<Project> tempList = projectService.getAllOwnedProjectsOfAUser(user.getId());
        boolean exists = false;
        for (Project pr : tempList) {
            if (pr.getProjectName().toLowerCase().equals(project.getProjectName().toLowerCase())) {
                exists = true;
            }
        }
        if (exists) {
            model.addAttribute("createProjectError", "Sorry. It seems you already have a project named that way");
            model.addAttribute("project", project);
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

        System.out.println(projectTeam.toString());
        return "redirect:/";
    }

    @PostMapping("/updateProjectDetails")
    public String updateProjectDetails(@Valid @ModelAttribute("project") Project project,
            BindingResult theBindingResult,
            Model model) {
        
        project = trimTheProject(project);

        // form validation
        if (theBindingResult.hasErrors()) {
            model.addAttribute("emptyTask", new Task());
            return "projectSetup";
        }

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
            Model model) {
        Project currentProject = projectService.getProjectbyid(projectid);
        model.addAttribute("user", userService.getLoggedinUser());
        model.addAttribute("project", currentProject);
        model.addAttribute("emptyTask", new Task());
        return "projectSetup";
    }

    @PostMapping("/deleteProject")
    public String deleteProject(@ModelAttribute("projectId") Long projectid,
            Model model) {
        projectService.deleteProject(projectService.getProjectbyid(projectid));
        return "redirect:/";
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
