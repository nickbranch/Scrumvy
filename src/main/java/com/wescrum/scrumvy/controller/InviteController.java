package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.entity.Invite;
import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.User;
import com.wescrum.scrumvy.service.InviteServiceInterface;
import com.wescrum.scrumvy.service.ProjectServiceInterface;
import com.wescrum.scrumvy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/invites")
public class InviteController {
    
    @Autowired
    InviteServiceInterface inviteService;
    @Autowired
    UserService userService;
    @Autowired
    ProjectServiceInterface projectService;

    @PostMapping("/sendInvite")
    public String sendInvite(@ModelAttribute("invite") Invite invite, 
            @ModelAttribute("userName") String userName, 
            @ModelAttribute("project") Long projectId,
            Model model) {
        Project project = projectService.getProjectbyid(projectId);
        User receivingUser = userService.findByUserName(userName);        
        invite.setAccepted(Boolean.FALSE);
        invite.setReceivingUserId(receivingUser);
        invite.setProjectId(project);
        inviteService.createInvite(invite);
        model.addAttribute(project);
        model.addAttribute("inviteHasBeenSent", "User invited.");
        return "manageTeam";
    }

}
