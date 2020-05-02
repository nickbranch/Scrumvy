package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.repos.ProjectRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectServiceInterface {

    @Autowired
    ProjectRepository projectRepository;

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
}
