/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Sprint;
import com.wescrum.scrumvy.repos.SprintRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SprintServiceImpl implements SprintServiceInterface {
    
    @Autowired
    SprintRepository sprintRepo;
    
    
    @Override
    public List<Sprint> getAllSprints() {
        return sprintRepo.findAll();
    }

    @Override
    public void createSprint(Sprint sprint) {
        sprintRepo.save(sprint);
    }

    @Override
    public Sprint getSprintbyid(Long id) {
        return sprintRepo.getOne(id);
    }

    @Override
    public void deleteSprint(Sprint sprint) {
        sprintRepo.delete(sprint);
    }

    @Override
    public void updateSprint(Sprint sprint) {
        sprintRepo.save(sprint);
    }
    
}
