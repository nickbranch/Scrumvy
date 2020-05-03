package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.ProjectRole;
import com.wescrum.scrumvy.entity.ProjectTeam;
import com.wescrum.scrumvy.entity.User;
import com.wescrum.scrumvy.repos.ProjectRoleRepository;
import com.wescrum.scrumvy.repos.ProjectTeamRepository;
import com.wescrum.scrumvy.service.ProjectServiceImpl;
import com.wescrum.scrumvy.service.ProjectTeamServiceImpl;
import com.wescrum.scrumvy.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private ProjectServiceImpl projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectTeamServiceImpl projectTeamService;
    @Autowired
    private ProjectRoleRepository projectRoleRepo;
    @Autowired
    ProjectTeamRepository projectTeamRepo;

    @GetMapping("/createProject")
    public String createProject(Model model) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        User user = userService.findByUserName(username);
        User customUser = userService.findByUserId(user.getId());

        ProjectRole projectRole = projectRoleRepo.findByprojectRoleId(1);
        List<ProjectTeam> usersOwnedProjects = projectTeamRepo.findByUserIdAndProjectRoleId(user, projectRole);
        System.out.println(usersOwnedProjects);

        int numberOfProjects = usersOwnedProjects.size(); // MUST CHECK FOR OWNED PROJECTS
        if ((customUser.getPremium() == false) && (numberOfProjects >= 1)) {
            // IMPLEMENT THROW ERROR
            model.addAttribute("project", new Project());
            model.addAttribute("createProjectError", "If you need to create more projects. Go premium!");
            return "home";
        } else {
            model.addAttribute("project", new Project());
            model.addAttribute("customUser", customUser);
            return "createProjectForm";
        }
    }

    @PostMapping("/saveProject")
    public String saveProject(@Valid @ModelAttribute("project") Project project,
            BindingResult theBindingResult,
            Model model) {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        System.out.println("logged in user = " + username); //diagnostic
        User user = userService.findByUserName(username);
        System.out.println("logged in user = " + user.getUsername()); //diagnostic

        User customUser = userService.findByUserId(user.getId());
        System.out.println("logged in user = " + customUser.getUsername()); //diagnostic

        // form validation
        if (theBindingResult.hasErrors()) {
            return "createProjectForm";
        }

        ProjectRole projectRole = projectRoleRepo.findByprojectRoleId(1);

        System.out.println("PROJECT ID = " + project.getProjectId()); //diagnostic
        System.out.println("PROJECT name = " + project.getProjectName()); //diagnostic

        projectService.createProject(project);

        System.out.println("PROJECT ID = " + project.getProjectId()); //diagnostic
        // create project team
        ProjectTeam projectTeam = new ProjectTeam();
        projectTeam.setProjectId(project);
        projectTeam.setUserId(user);
        projectTeam.setProjectRoleId(projectRole);
        projectTeamService.saveTeam(projectTeam);

        user.getProjectsCollection().add(project);
        userService.saveUserWithProject(user);

        System.out.println(projectTeam.toString());
        //customUser.getProjectsCollection().add(project);
        //userService.saveUserWithProject(customUser);
        return "home";
    }
}
