/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Retrospective;
import com.wescrum.scrumvy.repos.RetrospectiveRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetrospectiveServiceImpl implements RetrospectiveServiceInterface {
    
    @Autowired
    RetrospectiveRepository retroRepo;

    @Override
    public List<Retrospective> getAllRetrospective() {
        return retroRepo.findAll();
    }

    @Override
    public void createRetrospective(Retrospective retrospective) {
        retroRepo.save(retrospective);
    }

    @Override
    public Retrospective getRetrospectivebyid(Long id) {
       return retroRepo.getOne(id);
    }

    @Override
    public void deleteRetrospective(Retrospective retrospective) {
       retroRepo.delete(retrospective);
    }

    @Override
    public void updateRetrospective(Retrospective retrospective) {
        retroRepo.save(retrospective);
    }
    
}
