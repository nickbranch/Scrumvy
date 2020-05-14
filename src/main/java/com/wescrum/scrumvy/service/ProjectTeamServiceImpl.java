package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.ProjectTeam;
import com.wescrum.scrumvy.repos.ProjectTeamRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTeamServiceImpl implements ProjectTeamServiceInterface {

    @Autowired
    ProjectTeamRepository projectTeamRepository;

    @Override
    public void saveTeam(ProjectTeam projectTeam) {
        projectTeamRepository.save(projectTeam);
    }

    @Override
    public ProjectTeam getProjectTeambyid(Long projectTeamId) {
        return projectTeamRepository.getOne(projectTeamId);
    }

    @Override
    public void deleteProjectTeam(ProjectTeam projectTeam) {
        projectTeamRepository.delete(projectTeam);
    }

    @Override
    public boolean checkIfATeamIsPartOfTheActiveProject(ProjectTeam projectTeamToFind, Project project) {
        List<ProjectTeam> teamsToCheck = projectTeamRepository.findByProjectId(project);
        boolean toggle = false;
        for (ProjectTeam projectTeam : teamsToCheck) {
            if (Objects.equals(projectTeam.getProjectTeamId(), projectTeamToFind.getProjectTeamId())) {
                toggle = true;
            }
        }
        return toggle;
    }

}
