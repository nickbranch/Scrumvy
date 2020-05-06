/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wescrum.scrumvy.repos;


import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.Sprint;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {
    
    
     public List<Sprint> findByProjectId(Project projectId);
    
}
