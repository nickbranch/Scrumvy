package com.wescrum.scrumvy.repos;

import com.wescrum.scrumvy.entity.ProjectTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, Long>{
    
}
