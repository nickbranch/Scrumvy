package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.ProjectTeam;

public interface ProjectTeamServiceInterface {

    public void saveTeam(ProjectTeam projectTeam);

    public ProjectTeam getProjectTeambyid(Long projectTeamId);

    public void deleteProjectTeam(ProjectTeam projectTeam);
    
    public boolean checkIfATeamIsPartOfTheActiveProject (ProjectTeam projectTeam, Project project);
}
