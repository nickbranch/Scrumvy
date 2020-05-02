package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Project;
import java.util.List;

public interface ProjectServiceInterface {

    public List<Project> getAllProjects();

    public void createProject(Project project);

    public Project getProjectbyid(Long id);

    public void deleteProject(Project project);

    public void updateProject(Project project);

}
