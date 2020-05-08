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

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectRoleRepository projectRoleRepo;

    @Autowired
    private ProjectTeamRepository projectTeamRepo;

    @GetMapping("/")
    public String showHome(ModelMap modelMap) {
        User user = userService.getLoggedinUser();
        String[] roles = {"ownedProjects", "joinedAsScrumMaster", "joinedAsDevTeam"};
        for (int i = 1; i <= 3; i++) {
            List<Project> listTobeAdded = userProjectListGenerator(i, user);
            modelMap.addAttribute(roles[i - 1], listTobeAdded);
        }
        return "home";
    }

    private List<Project> userProjectListGenerator(Integer roleId, User user) {
        ProjectRole projectRole = projectRoleRepo.findByprojectRoleId(roleId);
        List<ProjectTeam> usersTeamProjects = projectTeamRepo.findByUserIdAndProjectRoleId(user, projectRole);
        List<Project> usersProjects = new ArrayList();

        for (ProjectTeam usersTeamProject : usersTeamProjects) {
            usersProjects.add(usersTeamProject.getProjectId());
        }
        return usersProjects;
    }

    // add request mapping for /systems
    @GetMapping("/systems")
    public String showSystems() {

        return "systems";
    }

}
