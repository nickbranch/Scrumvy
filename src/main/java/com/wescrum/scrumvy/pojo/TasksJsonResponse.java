package com.wescrum.scrumvy.pojo;

public class TasksJsonResponse {

    // CARE THE DATES, THEY ARE SPRINT DATES
    private Long taskId;
    private String description;
    private Integer statusId;
    private String sprintStartDate;
    private String sprintEndDate;

    public TasksJsonResponse(Long taskId, String description, Integer statusId, String sprintStartDate, String sprintEndDate) {
        this.taskId = taskId;
        this.description = description;
        this.statusId = statusId;
        this.sprintStartDate = sprintStartDate;
        this.sprintEndDate = sprintEndDate;
    }

    public TasksJsonResponse(String sprintStartDate, String sprintEndDate) {
        this.sprintStartDate = sprintStartDate;
        this.sprintEndDate = sprintEndDate;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getSprintStartDate() {
        return sprintStartDate;
    }

    public void setSprintStartDate(String sprintStartDate) {
        this.sprintStartDate = sprintStartDate;
    }

    public String getSprintEndDate() {
        return sprintEndDate;
    }

    public void setSprintEndDate(String sprintEndDate) {
        this.sprintEndDate = sprintEndDate;
    }

    @Override
    public String toString() {
        return "TasksJsonResponse{" + "taskId=" + taskId + ", description=" + description + ", statusId=" + statusId + ", sprintStartDate=" + sprintStartDate + ", sprintEndDate=" + sprintEndDate + '}';
    }
}
