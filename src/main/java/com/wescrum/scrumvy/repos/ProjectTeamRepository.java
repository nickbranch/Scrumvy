package com.wescrum.scrumvy.repos;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.ProjectRole;
import com.wescrum.scrumvy.entity.ProjectTeam;
import com.wescrum.scrumvy.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTeamRepository extends JpaRepository<ProjectTeam, Long>{
    
    public List<ProjectTeam> findByUserIdAndProjectRoleId(User user_id,ProjectRole project_role_id);
    public List<ProjectTeam> findByProjectId(Project project);
    
}
