package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.ProjectTeam;

public interface ProjectTeamServiceInterface {
//    public List<Project> getAllProjects();

    public void saveTeam(ProjectTeam projectTeam);

    public ProjectTeam getProjectTeambyid(Long projectTeamId);
//
    public void deleteProjectTeam(ProjectTeam projectTeam);
//
//    public void updateProject(Project project);
}
