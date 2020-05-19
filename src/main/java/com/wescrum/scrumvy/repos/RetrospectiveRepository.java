package com.wescrum.scrumvy.repos;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.Retrospective;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetrospectiveRepository extends JpaRepository <Retrospective, Long> {
    
     public List<Retrospective> findByProjectId (Project projectId);
     
}