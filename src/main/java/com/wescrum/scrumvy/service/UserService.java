package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.dto.UserDto;
import com.wescrum.scrumvy.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public User findByUserName(String userName);

    public User findByUserId(Integer userId);

    public void save(UserDto userDto);

    public void saveUserWithProject(User user);

    public User getLoggedinUser();
}
