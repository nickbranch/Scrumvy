package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.ProjectTeam;
import com.wescrum.scrumvy.repos.ProjectTeamRepository;
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
    
}
