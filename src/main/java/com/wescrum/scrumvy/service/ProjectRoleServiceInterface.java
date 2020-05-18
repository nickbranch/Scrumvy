/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.ProjectRole;
import java.util.List;

/**
 *
 * @author user
 */
public interface ProjectRoleServiceInterface {
    
    public List<ProjectRole> getAllProjectRoles();

    public void createProjectRole(ProjectRole projectRole);

    public ProjectRole getProjectRolebyid(Integer projectRoleId);

    public void deleteProjectRole(ProjectRole projectRole);

    public void updateProjectRole(ProjectRole projectRole);
}
