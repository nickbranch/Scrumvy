package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.entity.Invite;
import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.User;
import com.wescrum.scrumvy.service.InviteServiceInterface;
import com.wescrum.scrumvy.service.ProjectServiceInterface;
import com.wescrum.scrumvy.service.UserService;
import javax.servlet.http.HttpServletRequest;
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
        if (inviteService.checkForDuplicate(invite)) {
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

//    @RequestMapping(params = "Accept", method = RequestMethod.GET)
//    public String handleAcceptRedirect(@ModelAttribute("theRecInvite") Invite invite, Model model,
//            final RedirectAttributes redirectAttributes) {
//        if (invite.getReceivingUserId().getId() != ProjectController.activeUser) {
//            redirectAttributes.addFlashAttribute("createProjectError", "Have you been playing with html :)?");
//            
//        }
//       // if (acceptInviteLogicCheck(invite)) {
//            // logic should check the active user, 
//            // check if project belongs to the user
//            // check if invite accepted status is still false
//            // 
//            // get the project
//            // get the user
//            // get the role
//            // create project team object and set those values
//            // save team dont know if it needs to be added to the project also
//            // delete all the invites of this project that have accepted status 0 in this specific role
//            // delete all the invites of this project to this specific user
//            // delete the invite
////        }
////        return "redirect:/";
////    }

    @RequestMapping(params = "Reject", method = RequestMethod.GET)
    public String handleRejectRedirect(@ModelAttribute("theRecInvite") Invite invite, Model model,
            final RedirectAttributes redirectAttributes) {
        if (invite.getReceivingUserId().getId() != ProjectController.activeUser) {
            redirectAttributes.addFlashAttribute("createProjectError", "Have you been playing with html :)?");

        }
        return "redirect:/";
    }

}
