
package com.wescrum.scrumvy.pojo;


public class TasksJsonResponse {
    
    private Long taskId;
    private String description;
    private Integer statusId;

    public TasksJsonResponse(Long taskId, String description, Integer statusId) {
        this.taskId = taskId;
        this.description = description;
        this.statusId = statusId;
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

    @Override
    public String toString() {
        return "TasksJsonResponse{" + "taskId=" + taskId + ", description=" + description + ", statusId=" + statusId + '}';
    }
    
    
    
    
}
