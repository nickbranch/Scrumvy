package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Retrospective;
import java.util.List;

public interface RetrospectiveServiceInterface {
    
    public List<Retrospective> getAllRetrospective();

    public void createRetrospective(Retrospective retrospective);

    public Retrospective getRetrospectivebyid(Long id);

    public void deleteRetrospective(Retrospective retrospective);

    public void updateRetrospective(Retrospective retrospective);
}