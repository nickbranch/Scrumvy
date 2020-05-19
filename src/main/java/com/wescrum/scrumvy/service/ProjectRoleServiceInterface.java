package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.ProjectRole;
import java.util.List;

public interface ProjectRoleServiceInterface {
    
    public List<ProjectRole> getAllProjectRoles();

    public void createProjectRole(ProjectRole projectRole);

    public ProjectRole getProjectRolebyid(Integer projectRoleId);

    public void deleteProjectRole(ProjectRole projectRole);

    public void updateProjectRole(ProjectRole projectRole);
}