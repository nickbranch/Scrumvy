package com.wescrum.scrumvy.controller;

import static com.wescrum.scrumvy.controller.ProjectController.activeUser;
import com.wescrum.scrumvy.entity.Invite;
import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.ProjectRole;
import com.wescrum.scrumvy.entity.ProjectTeam;
import com.wescrum.scrumvy.entity.User;
import com.wescrum.scrumvy.repos.ProjectRoleRepository;
import com.wescrum.scrumvy.repos.ProjectTeamRepository;
import com.wescrum.scrumvy.repos.UserRepository;
import com.wescrum.scrumvy.service.ProjectServiceInterface;
import com.wescrum.scrumvy.service.ProjectTeamServiceInterface;
import com.wescrum.scrumvy.service.UserService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private ProjectTeamRepository projectTeamRepo;

    @PostMapping("/manageTeamMembers")
    public String manageTeamMembers(@ModelAttribute("projectId") Long projectid,
            Model model,
            final RedirectAttributes redirectAttributes) {

        //we can apply some ban here
        if (ProjectController.activeProject != projectid) {
            redirectAttributes.addFlashAttribute("createProjectError", "Please do not tamper with hidden form fields.");
            return "redirect:/";
        }
        Project project = projectService.getProjectbyid(projectid);
        ArrayList<ProjectRole> roleList = findAvailableRoles(project);
        model.addAttribute("availableRoles", roleList);
        model.addAttribute("project", project);
        return "manageTeam";
    }

    @PostMapping("/releaseTeamMember")
    public String releaseTeamMember(@ModelAttribute("projectTeamId") Long projectTeamId,
            Model model,
            final RedirectAttributes redirectAttributes) {
        ProjectTeam projectTeam = projectTeamService.getProjectTeambyid(projectTeamId);
        Project project = projectService.getProjectbyid(ProjectController.activeProject);
        if (projectTeamService.checkIfATeamIsPartOfTheActiveProject(projectTeam, project)) {
            projectTeamService.deleteProjectTeam(projectTeam);
            ArrayList<ProjectRole> roleList = findAvailableRoles(project);
            model.addAttribute("availableRoles", roleList);
            model.addAttribute("project", project);
            return "manageTeam";
        } else {
            redirectAttributes.addFlashAttribute("createProjectError", "Please do not tamper with hidden form fields.");
            return "redirect:/";
        }
    }

    @PostMapping("/searchTeamMember")
    public String searchTeamMember(@RequestParam("searchTerm") String searchTerm,
            @ModelAttribute("projectId") Long projectId,
            final RedirectAttributes redirectAttributes,
            Model model) {
        //we can apply some ban here
        if (ProjectController.activeProject != projectId) {
            redirectAttributes.addFlashAttribute("createProjectError", "Please do not tamper with hidden form fields.");
            return "redirect:/";
        }
        Project project = projectService.getProjectbyid(projectId);
        String userToBeFound = searchTerm.trim();
        User loggedInUser = userService.getLoggedinUser();

        if (userToBeFound.equals("")) {
            model.addAttribute("couldNotFind", "Please enter a valid search term (Another Users Email Address).");
            model.addAttribute("project", project);
            return "manageTeam";
        }

        User user = userRepo.findByEmail(userToBeFound);
        if ((user != null) && (user != loggedInUser)) {
            Invite invite = new Invite();
            model.addAttribute("userFound", user.getUsername());
            model.addAttribute("project", project);
            model.addAttribute("invite", invite);
            ArrayList<ProjectRole> roleList = findAvailableRoles(project);
            model.addAttribute("availableRoles", roleList);
            return "manageTeam";
        } else {
            model.addAttribute("couldNotFind", "User Not Found! Mail system Invite will come in future update.");
            model.addAttribute("project", project);
            return "manageTeam";
        }
    }

    // We scan the project for the positions and if scrumMasterExists (2)
    // we act accordingly
    private ArrayList<ProjectRole> findAvailableRoles(Project project) {
        Collection<ProjectTeam> tempList = project.getProjectTeamCollection();
        ArrayList<ProjectRole> roleList = new ArrayList<>();
        boolean scrumMasterExists = false;
        for (ProjectTeam projectTeam : tempList) {
            if (projectTeam.getProjectRoleId().getProjectRoleId() == 2) {
                scrumMasterExists = true;
            }
        }
        if (scrumMasterExists) {
            roleList.add(projectRoleRepo.findByprojectRoleId(3));
        } else {
            roleList.add(projectRoleRepo.findByprojectRoleId(2));
            roleList.add(projectRoleRepo.findByprojectRoleId(3));
        }
        return roleList;
    }

}
