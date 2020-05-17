package com.wescrum.scrumvy.dao;

import com.wescrum.scrumvy.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
