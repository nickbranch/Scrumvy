package com.wescrum.scrumvy.repos;

import com.wescrum.scrumvy.entity.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Integer> {
    public ProjectRole findByprojectRoleId(Integer projectRoleId);
}
