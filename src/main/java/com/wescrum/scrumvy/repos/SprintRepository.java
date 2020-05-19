package com.wescrum.scrumvy.repos;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.Sprint;
import java.util.Date;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {    

    public List<Sprint> findByProjectId(Project projectId);
     
     public List<Sprint> findByProjectIdAndSprintStartDateBeforeAndSprintEndDateAfter(Project projectId, Date date1, Date date2);
     
     public Optional<Sprint> findByProjectIdAndSprintId(Project projectId, Long sprintId);
}