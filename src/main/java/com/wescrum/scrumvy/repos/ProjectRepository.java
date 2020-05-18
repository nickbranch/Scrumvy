package com.wescrum.scrumvy.repos;

import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.Sprint;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {


    
}
