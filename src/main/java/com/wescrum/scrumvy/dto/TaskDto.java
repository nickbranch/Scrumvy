package com.wescrum.scrumvy.dto;
import java.util.Date;

public class TaskDto {

    private String description;
    private Date taskStartDate;
    private Date taskEndDate;
    private Long projectId;
    
    public TaskDto() {
        
    }
    
    public TaskDto(String description, Date taskStartDate, Date taskEndDate, Long projectId) {
        this.description = description;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
        this.projectId = projectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(Date taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public Date getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(Date taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "TaskDto{" + "description=" + description + ", taskStartDate=" + taskStartDate + ", taskEndDate=" + taskEndDate + ", projectId=" + projectId + '}';
    }    
}
