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
import com.wescrum.scrumvy.repos.ProjectRepository;
import com.wescrum.scrumvy.repos.ProjectRoleRepository;
import com.wescrum.scrumvy.repos.ProjectTeamRepository;
import com.wescrum.scrumvy.repos.RetrospectiveRepository;
import com.wescrum.scrumvy.repos.SprintRepository;
import com.wescrum.scrumvy.repos.TaskRepository;
import com.wescrum.scrumvy.service.ProjectServiceImpl;
import com.wescrum.scrumvy.service.ProjectTeamServiceImpl;
import com.wescrum.scrumvy.service.TaskServiceInterface;
import com.wescrum.scrumvy.service.UserService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectServiceImpl projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectTeamServiceImpl projectTeamService;
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

    @GetMapping("/createProject")
    public String createProject(Model model) {
        User user = userService.getLoggedinUser();

        ProjectRole projectRole = projectRoleRepo.findByprojectRoleId(1);
        List<ProjectTeam> usersOwnedProjects = projectTeamRepo.findByUserIdAndProjectRoleId(user, projectRole);

        int numberOfProjects = usersOwnedProjects.size();
        if ((user.getPremium() == false) && (numberOfProjects >= 1)) {
            model.addAttribute("createProjectError", "I would love to create a new project for you. If you want me to do this Go premium!");
            return "forward:/";
        } else {
            model.addAttribute("project", new Project());
            model.addAttribute("customUser", user);
            return "createProjectForm";
        }
    }

    @PostMapping("/saveProject")
    public String saveProject(@Valid @ModelAttribute("project") Project project,
            BindingResult theBindingResult,
            Model model) {

        User user = userService.getLoggedinUser();

        // form validation
        if (theBindingResult.hasErrors()) {
            return "createProjectForm";
        }

        List<Project> tempList = projectService.getAllOwnedProjectsOfAUser(user.getId());
        boolean exists = false;
        for (Project pr : tempList) {
            if (pr.getProjectName().toLowerCase().equals(project.getProjectName().toLowerCase())) {
                exists = true;
            }
        }
        if (exists) {
            model.addAttribute("createProjectError", "Sorry. It seems you already have a project named that way");
            model.addAttribute("project", project);
            return "createProjectForm";
        }

        ProjectRole projectRole = projectRoleRepo.findByprojectRoleId(1);
        projectService.createProject(project);

        // create project team
        ProjectTeam projectTeam = new ProjectTeam();
        projectTeam.setProjectId(project);
        projectTeam.setUserId(user);
        projectTeam.setProjectRoleId(projectRole);
        projectTeamService.saveTeam(projectTeam);

        user.getProjectsCollection().add(project);
        userService.saveUserWithProject(user);

        System.out.println(projectTeam.toString());
        return "redirect:/";
    }

    @GetMapping("/projectDetails/{id}")
    public String showProject(@PathVariable Long id,
            Model model) {
        User user = userService.getLoggedinUser();//chat
        String loggedInUser = user.getUsername();//chat
        model.addAttribute("loggedInUser", loggedInUser);//chat

        Project currentProject = projectService.getProjectbyid(id);
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

        //Chat
        List<String> chatUserNames = new ArrayList<>();
        for (ProjectTeam projectChatTeam : projectTeam) {
            chatUserNames.add(projectChatTeam.getUserId().getUsername());
        }

        model.addAttribute("chatUserNames", chatUserNames);

        return "workspace";
    }

}
