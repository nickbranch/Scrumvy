package com.wescrum.scrumvy.dao;

import com.wescrum.scrumvy.entity.User;

public interface UserDao {

    public User findByUserName(String userName);
    public void save(User user);

}
