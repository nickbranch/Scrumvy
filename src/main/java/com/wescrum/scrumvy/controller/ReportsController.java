package com.wescrum.scrumvy.controller;

import com.wescrum.scrumvy.entity.DailyScrumRecord;
import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.Retrospective;
import com.wescrum.scrumvy.entity.Sprint;
import com.wescrum.scrumvy.entity.Task;
import com.wescrum.scrumvy.repos.DailyScrumRecordRepository;
import com.wescrum.scrumvy.repos.RetrospectiveRepository;
import com.wescrum.scrumvy.repos.SprintRepository;
import com.wescrum.scrumvy.repos.TaskRepository;
import com.wescrum.scrumvy.service.ProjectServiceInterface;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportsController {

    @Autowired
    private SprintRepository sprintRepo;
    @Autowired
    private TaskRepository taskRepo;
    @Autowired
    private ProjectServiceInterface projectService;
    @Autowired
    private DailyScrumRecordRepository dailyScrumRepo;
    @Autowired
    private RetrospectiveRepository retroRepo;

    @GetMapping("showMeAllSprints")
    public String showMeAllSprints(HttpServletRequest request, Model model) {
        List<Sprint> showMeAllSprints = sprintRepo.findByProjectId(getTheActiveProject(request));
        model.addAttribute("showMeAllSprints", showMeAllSprints);
        return "showMeAllSprintsReport";
    }

    @GetMapping("showMeAllTasksPerSprint")
    public String showMeAllTasksPerSprint(HttpServletRequest request, Model model) {
        List<Sprint> showMeAllSprints = sprintRepo.findByProjectId(getTheActiveProject(request));
        model.addAttribute("showMeAllTasksPerSprint", showMeAllSprints);
        return "showMeAllTasksPerSprintReport";
    }

    @GetMapping("showMeTheProductBackLog")
    public String showMeTheProductBackLog(HttpServletRequest request, Model model) {
        List<Task> showMeTheProductBackLog = taskRepo.findByProjectId(getTheActiveProject(request));
        model.addAttribute("showMeTheProductBackLog", showMeTheProductBackLog);
        return "showMeTheProductBackLogReport";
    }

    @GetMapping("showMeAllTheDailyScrum")
    public String showMeAllTheDailyScrum(HttpServletRequest request, Model model) {
        List<DailyScrumRecord> showMeAllTheDailyScrum = dailyScrumRepo.findByProjectId(getTheActiveProject(request));
        model.addAttribute("showMeAllTheDailyScrum", showMeAllTheDailyScrum);
        return "showMeAllTheDailyScrumReport";
    }

    @GetMapping("showMeAllTheRetrospective")
    public String showMeAllTheRetrospective(HttpServletRequest request, Model model) {
        List<Retrospective> showMeAllTheRetrospective = retroRepo.findByProjectId(getTheActiveProject(request));
        model.addAttribute("showMeAllTheRetrospective", showMeAllTheRetrospective);
        return "showMeAllTheRetrospectiveReport";
    }

    @GetMapping("showMeAFullProjectReport")
    public String showMeAFullProjectReport(HttpServletRequest request, Model model) {
        Project activeProject = getTheActiveProject(request);
        model.addAttribute("activeProject", activeProject);
        return "showMeAFullProjectReportView";
    }

    @GetMapping("reportCentral")
    public String reportCentral() {
        return "reportCentral";
    }

    private Project getTheActiveProject(HttpServletRequest request) {
        Long currentProjectId = (Long) request.getSession().getAttribute("activeProject");
        return projectService.getProjectbyid(currentProjectId);
    }

}
