package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Status;
import com.wescrum.scrumvy.repos.StatusRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusServiceInterface {
    
    @Autowired
    StatusRepository statusRepo;

    @Override
    public List<Status> getAllStatus() {
       return statusRepo.findAll();
    }

    @Override
    public void createStatus(Status status) {
        statusRepo.save(status);
    }

    @Override
    public Status getStatusbyid(Integer id) {
        return statusRepo.getOne(id);
    }

    @Override
    public void deleteStatus(Status status) {
       statusRepo.delete(status);
    }

    @Override
    public void updateStatus(Status status) {
        statusRepo.save(status);
    }    
}