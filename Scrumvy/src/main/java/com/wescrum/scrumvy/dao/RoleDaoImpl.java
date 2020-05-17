package com.wescrum.scrumvy.dao;

import com.wescrum.scrumvy.entity.Role;
import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Role findRoleByName(String roleName) {

        // get the current hibernate session  (!!!)
        Session currentSession = entityManager.unwrap(Session.class);

        // now retrieve/read from database using name
        Query<Role> theQuery = currentSession.createQuery("from Role where name=:roleName", Role.class);
        theQuery.setParameter("roleName", roleName);

        Role role = null;

        try {
            role = theQuery.getSingleResult();
        } catch (Exception e) {
            role = null;
        }

        return role;
    }
}
