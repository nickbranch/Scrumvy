package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.ProjectTeam;
import com.wescrum.scrumvy.repos.ProjectRepository;
import com.wescrum.scrumvy.repos.ProjectRoleRepository;
import com.wescrum.scrumvy.repos.ProjectTeamRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectServiceInterface {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProjectTeamRepository projectTeamRepo;
    @Autowired
    UserService userService;
    @Autowired
    ProjectRoleRepository projectRoleRepo;

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public void createProject(Project project) {
        projectRepository.save(project);
    }

    @Override
    public Project getProjectbyid(Long id) {
        return projectRepository.getOne(id);
    }

    @Override
    public void deleteProject(Project project) {
        projectRepository.delete(project);
    }

    @Override
    public void updateProject(Project project) {
        projectRepository.save(project);
    }

    @Override
    public List<Project> getAllOwnedProjectsOfAUser(Integer user_id) {
        List<Project> ownedProjectOfAUser = new ArrayList<>();
        List<ProjectTeam> ownedProjectOfAUserLookup = 
                projectTeamRepo.findByUserIdAndProjectRoleId(userService.findByUserId(user_id), projectRoleRepo.findByprojectRoleId(1));
        ownedProjectOfAUserLookup.forEach((projectTeam) -> {
            ownedProjectOfAUser.add(projectTeam.getProjectId());
        });
        return ownedProjectOfAUser;
    }
}
