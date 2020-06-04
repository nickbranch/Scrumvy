package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.entity.DailyScrumRecord;
import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.ProjectRole;
import com.wescrum.scrumvy.entity.ProjectTeam;
import com.wescrum.scrumvy.entity.Retrospective;
import com.wescrum.scrumvy.entity.Sprint;
import com.wescrum.scrumvy.entity.Task;
import com.wescrum.scrumvy.entity.User;
import com.wescrum.scrumvy.repos.DailyScrumRecordRepository;
import com.wescrum.scrumvy.repos.ProjectRoleRepository;
import com.wescrum.scrumvy.repos.ProjectTeamRepository;
import com.wescrum.scrumvy.repos.RetrospectiveRepository;
import com.wescrum.scrumvy.repos.SprintRepository;
import com.wescrum.scrumvy.repos.TaskRepository;
import com.wescrum.scrumvy.service.ProjectRoleServiceImpl;
import com.wescrum.scrumvy.service.ProjectServiceInterface;
import com.wescrum.scrumvy.service.ProjectTeamServiceInterface;
import com.wescrum.scrumvy.service.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectServiceInterface projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectTeamServiceInterface projectTeamService;
    @Autowired
    private ProjectRoleRepository projectRoleRepo;
    @Autowired
    private ProjectTeamRepository projectTeamRepo;
    @Autowired
    private TaskRepository taskRepo;
    @Autowired
    private SprintRepository sprintRepo;
    @Autowired
    private DailyScrumRecordRepository dailyScrumRepo;
    @Autowired
    private RetrospectiveRepository retroRepo;
    @Autowired
    private ProjectRoleServiceImpl projectRoleService;

    @GetMapping("/createProject")
    public String createProject(Model model, final RedirectAttributes redirectAttributes) {
        User user = userService.getLoggedinUser();

        ProjectRole projectRole = projectRoleRepo.findByprojectRoleId(1);
        List<ProjectTeam> usersOwnedProjects = projectTeamRepo.findByUserIdAndProjectRoleId(user, projectRole);

        if ((user.getPremium() == false) && (usersOwnedProjects.size() >= 1)) {
            redirectAttributes.addFlashAttribute("createProjectError", "I would love to create a new project for you. If you want me to do this Go premium!");
            return "redirect:/";
        } else {
            model.addAttribute("project", new Project());
            model.addAttribute("customUser", user);
            return "createProjectForm";
        }
    }

    @PostMapping("/saveProject")
    public String saveProject(@Valid @ModelAttribute("project") Project project,
            BindingResult theBindingResult,
            @ModelAttribute("userCollection") Long formUser,
            final RedirectAttributes redirectAttributes,
            Model model) {
        User user = userService.getLoggedinUser();
        if (theBindingResult.hasErrors()) {
            model.addAttribute("customUser", user);
            model.addAttribute("project", new Project());
            return "createProjectForm";
        }
        project = trimTheProject(project);

        //we can apply some ban here
        if (formUser != Long.valueOf(user.getId())) {
            redirectAttributes.addFlashAttribute("createProjectError", "Please do not tamper with hidden form fields.");
            return "redirect:/";
        }

        //unique name of project/user
        List<Project> tempList = projectService.getAllOwnedProjectsOfAUser(user.getId());
        boolean exists = false;
        for (Project pr : tempList) {
            if (pr.getProjectName().toLowerCase().equals(project.getProjectName().toLowerCase())) {
                exists = true;
            }
        }
        if (exists) {
            model.addAttribute("createProjectError", "Sorry. It seems you already have a project named that way");
            model.addAttribute("project", new Project());
            model.addAttribute("customUser", user);
            return "createProjectForm";
        }

        ProjectRole projectRole = projectRoleRepo.findByprojectRoleId(1);
        projectService.createProject(project);

        ProjectTeam projectTeam = new ProjectTeam(projectRole, project, user);
        projectTeamService.saveTeam(projectTeam);

        user.getProjectsCollection().add(project);
        userService.saveUserWithProject(user);

        return "redirect:/";
    }

    @PostMapping("/updateProjectDetails")
    public String updateProjectDetails(@Valid @ModelAttribute("project") Project project,
            BindingResult theBindingResult,
            @ModelAttribute("projectId") Long formProject, @ModelAttribute("userCollection") Long formUser,
            final RedirectAttributes redirectAttributes,
            HttpServletRequest request,
            Model model) {
        // form validation
        if (theBindingResult.hasErrors()) {
            prepareProjectSetupPage(model, project);
            return "projectSetup";
        }
        project = trimTheProject(project);

        //we can apply some ban here
        Long currentUserId = Long.valueOf(userService.getLoggedinUser().getId());
        if ((formProject != request.getSession().getAttribute("activeProject"))
                || (formUser != currentUserId)) {
            redirectAttributes.addFlashAttribute("createProjectError", "Please do not tamper with hidden form fields.");
            return "redirect:/";
        }

        // check if project is owned by this user
        if (!projectService.checkIdOfOwnedProjectsFix(project)) {
            redirectAttributes.addFlashAttribute("createProjectError", "Please do not tamper with hidden form fields.");
            return "redirect:/";
        }

        // unique name per project validation
        if (checkForSameName(project)) {
            model.addAttribute("createProjectError", "Sorry. It seems you already have a project named that way");
            prepareProjectSetupPage(model, project);
            return "projectSetup";
        }

        redirectAttributes.addFlashAttribute("projectId", project.getProjectId());

        projectService.createProject(project);
        return "redirect:/project/projectSettings";
    }

    @RequestMapping(value = "/projectSettings", method = RequestMethod.GET)
    public String projectSettings(@ModelAttribute("projectId") Long projectId,
            Model model, HttpServletRequest request,
            final RedirectAttributes redirectAttributes
    ) {
        Project project = projectService.getProjectbyid(projectId);
        if (projectService.checkIdOfOwnedProjectsFix(project) && (request.getSession().getAttribute("activeProject") == null) || 
                (projectId == request.getSession().getAttribute("activeProject"))) {
            request.getSession().setAttribute("activeProject", project.getProjectId());
            prepareProjectSetupPage(model, project);
            return "projectSetup";
        } else {
            redirectAttributes.addFlashAttribute("createProjectError", "Try to join your project by clicking on edit project.");
            return "redirect:/";
        }
    }

    @PostMapping("/deleteProject")
    public String deleteProject(@Valid @ModelAttribute("projectId") Long projectid,
            @ModelAttribute("projectId") Long formProject,
            final RedirectAttributes redirectAttributes,
            Model model,
            HttpServletRequest request) {
        //we can apply some ban here
        if ((formProject != request.getSession().getAttribute("activeProject"))) {
            redirectAttributes.addFlashAttribute("createProjectError", "Please do not tamper with hidden form fields.");
            return "redirect:/";
        }

        Project project = projectService.getProjectbyid(projectid);
        if (projectService.checkIdOfOwnedProjectsFix(project)) {
            projectService.deleteProject(projectService.getProjectbyid(projectid));
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("createProjectError", "Please do not tamper with hidden form fields.");
            return "redirect:/";
        }
    }

    private Project trimTheProject(Project project) {
        String trimmedName = project.getProjectName().trim();
        String trimmedDescription = project.getProjectDescription().trim();
        project.setProjectName(trimmedName);
        project.setProjectDescription(trimmedDescription);
        return project;
    }

    private boolean checkForSameName(Project project) {
        List<Project> tempList = projectService.getAllOwnedProjectsOfAUser(userService.getLoggedinUser().getId());
        for (Project pr : tempList) {
            if ((pr.getProjectName().toLowerCase().equals(project.getProjectName().toLowerCase())) && (!Objects.equals(pr.getProjectId(), project.getProjectId()))) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/projectDetails/{id}")
    public String showProject(@PathVariable Long id,
            Model model,
            HttpServletRequest request,
            final RedirectAttributes redirectAttributes) {

        Project currentProject = projectService.getProjectbyid(id);
        User user = userService.getLoggedinUser();

        request.getSession().setAttribute("activeProject", currentProject.getProjectId());

        if (userService.checkIfUserIsPartOfProject(user, currentProject)) {

            List<Task> projectTasks = taskRepo.findByProjectId(currentProject);
            List<Sprint> projectSprints = sprintRepo.findByProjectId(currentProject);
            List<ProjectTeam> projectTeam = projectTeamRepo.findByProjectId(currentProject);
            List<Retrospective> retroList = retroRepo.findByProjectId(currentProject);
            List<DailyScrumRecord> dailyScrumList = dailyScrumRepo.findByProjectId(currentProject);

            // get current sprint according to current date:
            Date date = new Date();
            List<Sprint> currentSprint = sprintRepo.findByProjectIdAndSprintStartDateBeforeAndSprintEndDateAfter(currentProject, date, date);

            model.addAttribute("project", currentProject);

            // current sprint:
            if (!currentSprint.isEmpty()) {
                model.addAttribute("currentSprint", currentSprint.get(0));
            }
            //all project tasks
            model.addAttribute("projectTasks", projectTasks);
            //all project sprints
            model.addAttribute("projectSprints", projectSprints);
            // project team
            model.addAttribute("projectTeam", projectTeam);
            // retro list
            model.addAttribute("retroList", retroList);
            // daily scrum list
            model.addAttribute("dailyScrumList", dailyScrumList);

            //CHECK ROLE TO GIVE ACCESS TO EDITS:
            boolean isProductOnwer = checkRoleForWorkspaceEdits(currentProject, user, 1);
            boolean isScrumMaster = checkRoleForWorkspaceEdits(currentProject, user, 2);

            model.addAttribute("isProductOwner", isProductOnwer);
            model.addAttribute("isScrumMaster", isScrumMaster);

            String loggedInUser = user.getUsername();//chat
            model.addAttribute("loggedInUser", loggedInUser);//chat

            //Chat
            List<String> chatUserNames = new ArrayList<>();
            for (ProjectTeam projectChatTeam : projectTeam) {
                chatUserNames.add(projectChatTeam.getUserId().getUsername());
            }

            model.addAttribute("chatUserNames", chatUserNames);

            return "workspace";
        } else {
            redirectAttributes.addFlashAttribute("createProjectError", "The project you are trying to join is not yours.");
            return "redirect:/";
        }
    }

    public boolean checkRoleForWorkspaceEdits(Project projectId, User id, int roleId) {
        ProjectRole projectRole = projectRoleService.getProjectRolebyid(roleId);
        switch (projectRole.getProjectRoleId()) {
            case 1:
                if ((projectTeamRepo.findByProjectIdAndUserIdAndProjectRoleId(projectId, id, projectRole)).isPresent()) {
                    return true;
                }
                ;
                break;
            case 2:
                if ((projectTeamRepo.findByProjectIdAndUserIdAndProjectRoleId(projectId, id, projectRole)).isPresent()) {
                    return true;
                }
                ;
                break;
            case 3:
                if ((projectTeamRepo.findByProjectIdAndUserIdAndProjectRoleId(projectId, id, projectRole)).isPresent()) {
                    return true;
                }
                ;
                break;
        }
        return false;
    }

    private void prepareProjectSetupPage(Model model, Project project) {
        model.addAttribute("user", userService.getLoggedinUser());
        model.addAttribute("project", project);
        Task task = new Task();
        task.setProjectId(project);
        model.addAttribute("emptyTask", task);
    }

}
