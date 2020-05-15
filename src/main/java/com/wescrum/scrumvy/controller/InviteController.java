package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.entity.Invite;
import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.ProjectTeam;
import com.wescrum.scrumvy.entity.User;
import com.wescrum.scrumvy.service.InviteServiceInterface;
import com.wescrum.scrumvy.service.ProjectServiceInterface;
import com.wescrum.scrumvy.service.ProjectTeamServiceInterface;
import com.wescrum.scrumvy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/invites")
public class InviteController {

    @Autowired
    InviteServiceInterface inviteService;
    @Autowired
    UserService userService;
    @Autowired
    ProjectServiceInterface projectService;
    @Autowired
    ProjectTeamServiceInterface projectTeamService;

    @PostMapping("/sendInvite")
    public String sendInvite(@ModelAttribute("invite") Invite invite,
            @ModelAttribute("userName") String userName,
            @ModelAttribute("project") Long projectId,
            final RedirectAttributes redirectAttributes,
            Model model) {

        if (projectId != ProjectController.activeProject) {
            redirectAttributes.addFlashAttribute("createProjectError", "Please do not tamper with hidden form fields.");
            return "redirect:/";
        }

        invite.setAccepted(Boolean.FALSE);
        invite.setReceivingUserId(userService.findByUserName(userName));
        invite.setProjectId(projectService.getProjectbyid(projectId));

        if (userService.checkIfUserIsPartOfAProject(invite)) {
            model.addAttribute("project", projectService.getProjectbyid(projectId));
            model.addAttribute("couldNotFind", "This person is already part of this project");
            return "manageTeam";
        } else if (inviteService.checkForDuplicate(invite)) {
            model.addAttribute("project", projectService.getProjectbyid(projectId));
            model.addAttribute("couldNotFind", "You have already invited that person to that position.");
            return "manageTeam";
        } else {
            inviteService.createInvite(invite);
            model.addAttribute("project", projectService.getProjectbyid(projectId));
            model.addAttribute("inviteHasBeenSent", "User invited.");
            return "manageTeam";
        }
    }

    @PostMapping("/handleAccept")
    public String handleAccept(@ModelAttribute("theRecInvite") Invite invite, Model model,
            final RedirectAttributes redirectAttributes) {        
        if (invite.getReceivingUserId().getId() != ProjectController.activeUser) {
            redirectAttributes.addFlashAttribute("createProjectError", "Have you been playing with html :)?");
        }
        
        if (inviteService.acceptInviteLogicCheck(invite)) {
            Project project = invite.getProjectId();
            User user = invite.getReceivingUserId();
            ProjectTeam projectTeam = new ProjectTeam(invite.getProjectRoleId(),project,user);
            // save team - save project - save user in order to inform both tables
            projectTeamService.saveTeam(projectTeam);
            project.getProjectTeamCollection().add(projectTeam);

            project.getUserCollection().add(user);

            projectService.createProject(project);
            System.out.println("**************************************************************************");
            // delete all the invites of this project that have role scrum master (there can be only one)
            // delete all the invites of this project to this specific user (user can only participate in one role)
            inviteService.performClearAfterAccept(invite);
            System.out.println("**************************************************************************");
        }
        return "redirect:/";
    }

    @RequestMapping(params = "Reject", method = RequestMethod.GET)
    public String handleRejectRedirect(@ModelAttribute("theRecInvite") Invite invite, Model model,
            final RedirectAttributes redirectAttributes) {
        if (invite.getReceivingUserId().getId() != ProjectController.activeUser) {
            redirectAttributes.addFlashAttribute("createProjectError", "Have you been playing with html :)?");

        }
        return "redirect:/";
    }

}
