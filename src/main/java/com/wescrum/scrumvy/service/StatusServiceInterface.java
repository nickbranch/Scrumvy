package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Status;
import java.util.List;

public interface StatusServiceInterface {

    public List<Status> getAllStatus();

    public void createStatus(Status status);

    public Status getStatusbyid(Integer id);

    public void deleteStatus(Status status);

    public void updateStatus(Status status);
}
