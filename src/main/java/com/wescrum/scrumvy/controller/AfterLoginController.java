package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.entity.Invite;
import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.ProjectRole;
import com.wescrum.scrumvy.entity.ProjectTeam;
import com.wescrum.scrumvy.entity.User;
import com.wescrum.scrumvy.repos.InviteRepository;
import com.wescrum.scrumvy.repos.ProjectRoleRepository;
import com.wescrum.scrumvy.repos.ProjectTeamRepository;
import com.wescrum.scrumvy.service.InviteServiceInterface;
import com.wescrum.scrumvy.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AfterLoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectRoleRepository projectRoleRepo;

    @Autowired
    private ProjectTeamRepository projectTeamRepo;

    @Autowired
    private InviteServiceInterface inviteService;

    @Autowired
    private InviteRepository inviteRepo;

    @Autowired
    private SessionRegistry sessionRegistry;
    public static ArrayList<User> loggedInUsers = new ArrayList<User>();

    @GetMapping("/")
    public String showHome(@ModelAttribute("createProjectError") String error, Model model) {
        User user = userService.getLoggedinUser();
        loggedInUsers.add(user);
        String[] roles = {"ownedProjects", "joinedAsScrumMaster", "joinedAsDevTeam"};
        List<Invite> sentInvites = inviteService.getSentInvites(user);
        List<Invite> receivedInvites = inviteRepo.findByReceivingUserId(user);

        System.out.println(listLoggedInUsers());

        for (int i = 1; i <= 3; i++) {
            List<Project> listTobeAdded = userProjectListGenerator(i, user);
            model.addAttribute(roles[i - 1], listTobeAdded);
        }
        if ("".equals(error)) {
            error = null;
        }
        model.addAttribute("createProjectError", error);
        model.addAttribute("sentInvites", sentInvites);
        model.addAttribute("receivedInvites", receivedInvites);
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

    public List<String> listLoggedInUsers() {
        List<Object> principals = sessionRegistry.getAllPrincipals();

        List<String> loggedUsers = new ArrayList<String>();

        for (Object principal : principals) {
            if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
                String userName = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
                loggedUsers.add(userName);
            }
        }
        return loggedUsers;
    }

    //adminpanel
    @GetMapping("/adminpanel")
    public String showAdminPanel() {

        return "adminPage";
    }

}
