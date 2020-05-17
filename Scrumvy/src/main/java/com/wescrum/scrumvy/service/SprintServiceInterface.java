
package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Sprint;
import java.util.List;


public interface SprintServiceInterface {
    
    
    public List<Sprint> getAllSprints();

    public void createSprint(Sprint sprint);

    public Sprint getSprintbyid(Long id);

    public void deleteSprint(Sprint sprint);

    public void updateSprint(Sprint sprint);
}
