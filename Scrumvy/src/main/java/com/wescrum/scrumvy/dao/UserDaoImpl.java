package com.wescrum.scrumvy.dao;

import com.wescrum.scrumvy.entity.User;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public User findByUserName(String userName) {
        // get the current hibernate session (!!!)
        Session currentSession = entityManager.unwrap(Session.class);

        // now retrieve/read from database using username
        Query<User> theQuery = currentSession.createQuery("from User where userName=:uName", User.class);
        theQuery.setParameter("uName", userName);
        User foundUser = null;
        try {
            foundUser = theQuery.getSingleResult();
        } catch (Exception e) {
            foundUser = null;
        }
        return foundUser;
    }

    @Override
    public void save(User user) {
        // get current hibernate session (!!!)
        Session currentSession = entityManager.unwrap(Session.class);
        // create the user
        currentSession.saveOrUpdate(user);
    }

}
