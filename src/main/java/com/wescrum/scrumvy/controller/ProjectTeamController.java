package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.ProjectTeam;
import com.wescrum.scrumvy.entity.User;
import com.wescrum.scrumvy.repos.ProjectRoleRepository;
import com.wescrum.scrumvy.repos.ProjectTeamRepository;
import com.wescrum.scrumvy.repos.UserRepository;
import com.wescrum.scrumvy.service.ProjectServiceInterface;
import com.wescrum.scrumvy.service.ProjectTeamServiceInterface;
import com.wescrum.scrumvy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/projectTeam")
public class ProjectTeamController {

    @Autowired
    private ProjectServiceInterface projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProjectTeamServiceInterface projectTeamService;
    @Autowired
    private ProjectRoleRepository projectRoleRepo;
    @Autowired
    ProjectTeamRepository projectTeamRepo;

    @PostMapping("/manageTeamMembers")
    public String manageTeamMembers(@ModelAttribute("projectId") Long projectid,
            Model model) {
        model.addAttribute("project", projectService.getProjectbyid(projectid));
        return "manageTeam";
    }

    @PostMapping("/releaseTeamMember")
    public String releaseTeamMember(@ModelAttribute("projectTeamId") Long projectTeamId,
            Model model) {
        ProjectTeam projectTeam = projectTeamService.getProjectTeambyid(projectTeamId);
        Project project = projectTeam.getProjectId();
        projectTeamService.deleteProjectTeam(projectTeam);
        model.addAttribute("project", project);
        return "manageTeam";
    }

    @PostMapping("/searchTeamMember")
    public String searchTeamMember(@RequestParam("searchTerm") String searchTerm,
            @ModelAttribute("projectId") Long projectId,
            Model model) {
        Project project = projectService.getProjectbyid(projectId);
        String userToBeFound = searchTerm.trim();
        User loggedInUser = userService.getLoggedinUser();
        
        if (userToBeFound.equals("")) {
            model.addAttribute("couldNotFind", "Please ener a valid search term.");
            model.addAttribute("project", project);
            return "manageTeam";
        }
        User user = userRepo.findByEmail(userToBeFound);
        if ((user != null) && (user!=loggedInUser)) {
            model.addAttribute("userFound", user);
            model.addAttribute("project", project);
            return "manageTeam";
        } else {
            model.addAttribute("couldNotFind", "User Not Found! Mail system Invite will come in future update.");
            model.addAttribute("project", project);
            return "manageTeam";
        }
    }

}
