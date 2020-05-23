package com.wescrum.scrumvy.controller.restController;

import com.wescrum.scrumvy.entity.DailyScrumRecord;
import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.Retrospective;
import com.wescrum.scrumvy.entity.Sprint;
import com.wescrum.scrumvy.entity.Status;
import com.wescrum.scrumvy.entity.Task;
import com.wescrum.scrumvy.pojo.TasksJsonResponse;
import com.wescrum.scrumvy.repos.SprintRepository;
import com.wescrum.scrumvy.service.DailyScrumRecordServiceInterface;
import com.wescrum.scrumvy.service.ProjectServiceInterface;
import com.wescrum.scrumvy.service.RetrospectiveServiceInterface;
import com.wescrum.scrumvy.service.SprintServiceInterface;
import com.wescrum.scrumvy.service.StatusServiceInterface;
import com.wescrum.scrumvy.service.TaskServiceInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkspaceController {

    @Autowired
    private TaskServiceInterface taskService;
    @Autowired
    private SprintServiceInterface sprintService;
    @Autowired
    private StatusServiceInterface statusService;
    @Autowired
    private RetrospectiveServiceInterface retroService;
    @Autowired
    private ProjectServiceInterface projectService;
    @Autowired
    private DailyScrumRecordServiceInterface dailyScrumService;
    @Autowired
    private SprintRepository sprintRepo;

    @PostMapping("/saveTask")
    public String saveTask(@ModelAttribute("task") Task task,
            @RequestParam("descriptionBefore") String descriptionBefore) {

        Task editedTask = taskService.getTaskbyid(task.getTaskId());
        editedTask.setDescription(task.getDescription());
        taskService.updateTask(editedTask);

        return "Successfully edited";

    }

    @PostMapping("/getSprintTasks")
    public List<TasksJsonResponse> getSprintTasks(@ModelAttribute("sprintId") Long sprintId,
            HttpServletRequest request) {
        Sprint sprint = sprintService.getSprintbyid(sprintId);
        List<TasksJsonResponse> listOfPojoTasks = new ArrayList();

        Long currentProjectId = (Long) request.getSession().getAttribute("activeProject");
        Project currentproject = projectService.getProjectbyid(currentProjectId);
        if ((sprintRepo.findByProjectIdAndSprintId(currentproject, sprintId)).isPresent()) {

            List<Task> sprintTasks = (List) sprint.getTaskCollection();
            System.out.println(sprintId + ":" + sprintTasks.toString());

            SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
            String sprintStartDateFormated = formatter.format(sprint.getSprintStartDate());
            String sprintEndDateFormated = formatter.format(sprint.getSprintEndDate());
            System.out.println(sprintStartDateFormated + "-" + sprintEndDateFormated);

            if (sprintTasks.isEmpty()) {
                listOfPojoTasks.add(new TasksJsonResponse(sprintStartDateFormated, sprintEndDateFormated));
                return listOfPojoTasks;
            }

            for (Task task : sprintTasks) {
                TasksJsonResponse pojoTask = new TasksJsonResponse(task.getTaskId(), task.getDescription(), task.getStatusId().getStatusId(), sprintStartDateFormated, sprintEndDateFormated);
                listOfPojoTasks.add(pojoTask);
                System.out.println(pojoTask);
            }

            return listOfPojoTasks;
        }
        return listOfPojoTasks;
    }

    @PostMapping("/updateTaskStatus")
    public String updateTaskStatus(@RequestParam("taskId") Long taskId,
            @RequestParam("statusId") Integer statusId) {
        Task task = taskService.getTaskbyid(taskId);
        Status status = statusService.getStatusbyid(statusId);
        task.setStatusId(status);
        taskService.createTask(task);
        return "Task status updated";
    }

    @PostMapping("/addRetrospective")
    public String addRetrospectiveToProject(@RequestParam("projectId") Long projectId,
            @RequestParam("description") String description) {
        Project project = projectService.getProjectbyid(projectId);
        Retrospective retro = new Retrospective();
        retro.setDescription(description);
        retro.setProjectId(project);
        retro.setTimestamp(new Date());
        retroService.createRetrospective(retro);
        return "Retro saved!";
    }

    @PostMapping("/addDailyScrum")
    public String addDailyScrum(@RequestParam("projectId") Long projectId,
            @RequestParam("description") String description) {

        Project project = projectService.getProjectbyid(projectId);
        DailyScrumRecord dailyScrum = new DailyScrumRecord();
        dailyScrum.setDescription(description);
        dailyScrum.setProjectId(project);
        dailyScrum.setTimestamp(new Date());
        dailyScrumService.createDailyScrumRecord(dailyScrum);
        return "Daily Scrum saved!";
    }
}
