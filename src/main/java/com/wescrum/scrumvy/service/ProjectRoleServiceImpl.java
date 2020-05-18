/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.ProjectRole;
import com.wescrum.scrumvy.repos.ProjectRoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectRoleServiceImpl implements ProjectRoleServiceInterface{
    
    @Autowired
    ProjectRoleRepository projectRoleRepo;

    @Override
    public List<ProjectRole> getAllProjectRoles() {
       return projectRoleRepo.findAll();
    }

    @Override
    public void createProjectRole(ProjectRole projectRole) {
        projectRoleRepo.save(projectRole);
    }

    @Override
    public ProjectRole getProjectRolebyid(Integer projectRoleId) {
       return projectRoleRepo.getOne(projectRoleId);
    }

    @Override
    public void deleteProjectRole(ProjectRole projectRole) {
       projectRoleRepo.delete(projectRole);
    }

    @Override
    public void updateProjectRole(ProjectRole projectRole) {
        projectRoleRepo.save(projectRole);
    }
    
}
