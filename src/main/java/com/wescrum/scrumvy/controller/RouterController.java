package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.Task;
import com.wescrum.scrumvy.service.ProjectServiceInterface;
import com.wescrum.scrumvy.service.UserService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RouterController {

    @Autowired
    private ProjectServiceInterface projectService;

    @Autowired
    private UserService userService;

    @GetMapping("/goPremium")
    public String goPremium() {
        return "buynow";
    }

    @GetMapping("/redirectToProject/{id}")
    public String redicrectToProject(@PathVariable Long id, Model model,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        Project currentProject = projectService.getProjectbyid(id);
        request.getSession().setAttribute("activeProject", currentProject.getProjectId());
        if (projectService.checkIdOfOwnedProjectsFix(currentProject)) {
            model.addAttribute("user", userService.getLoggedinUser());
            model.addAttribute("project", currentProject);
            Task task = new Task();
            task.setProjectId(currentProject);
            model.addAttribute("emptyTask", task);
            return "projectSetup";
        } else {
            redirectAttributes.addFlashAttribute("createProjectError", "The project you are trying to join is not yours.");
            return "redirect:/";
        }
    }

    @GetMapping("/redirectToWorkspace")
    public String redirectToWorkspace(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Long currentProjectId = (Long) request.getSession().getAttribute("activeProject");
        Project currentproject = projectService.getProjectbyid(currentProjectId);
        if (projectService.checkIdOfOwnedProjectsFix(currentproject)) {
            return "redirect:/project/projectDetails/" + currentProjectId;
        } else {
            redirectAttributes.addFlashAttribute("createProjectError", "The project you are trying to join is not yours.");
            return "redirect:/";
        }
    }

}
