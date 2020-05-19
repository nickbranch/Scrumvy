package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.dto.UserDto;
import com.wescrum.scrumvy.entity.Invite;
import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public User findByUserName(String userName);

    public User findByUserId(Integer userId);

    public void save(UserDto userDto);

    public void saveUserWithProject(User user);

    public User getLoggedinUser();
    
    public boolean checkIfUserIsPartOfAProject(Invite invite); //invite implementation
    
    public boolean checkIfUserIsPartOfProject(User user, Project project); //validation implementation
}
