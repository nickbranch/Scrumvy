package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.ProjectRole;
import com.wescrum.scrumvy.entity.ProjectTeam;
import com.wescrum.scrumvy.entity.User;
import com.wescrum.scrumvy.repos.ProjectRoleRepository;
import com.wescrum.scrumvy.repos.ProjectTeamRepository;
import com.wescrum.scrumvy.service.ProjectServiceInterface;
import com.wescrum.scrumvy.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AfterLoginController {

    @Autowired//diko mou
    private UserService userService;

    @Autowired
    private ProjectRoleRepository projectRoleRepo;

    @Autowired
    private ProjectTeamRepository projectTeamRepo;

    @Autowired
    private ProjectServiceInterface projectServiceInterface;

    @GetMapping("/")
    public String showHome(ModelMap modelMap) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        System.out.println("logged in user = " + username); //diagnostic
        User user = userService.findByUserName(username);
        User customUser = userService.findByUserId(user.getId());

        ProjectRole projectRole = projectRoleRepo.findByprojectRoleId(1);
        List<ProjectTeam> usersTeamProjects = projectTeamRepo.findByUserIdAndProjectRoleId(user, projectRole);

        List<Project> userOwnedProjects = new ArrayList();
        for (ProjectTeam usersTeamProject : usersTeamProjects) {
            System.out.println(usersTeamProjects);
            userOwnedProjects.add(projectServiceInterface.getProjectbyid(usersTeamProject.getProjectId().getProjectId()));
        }

        modelMap.addAttribute("ownedProjects", userOwnedProjects);
        return "home";
    }

    // add request mapping for /leaders
    @GetMapping("/leaders")
    public String showLeaders() {

        return "leaders";
    }

    // add request mapping for /systems
    @GetMapping("/systems")
    public String showSystems() {

        return "systems";
    }

}
