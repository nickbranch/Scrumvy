package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.dto.UserDto;
import com.wescrum.scrumvy.dao.RoleDao;
import com.wescrum.scrumvy.dao.UserDao;
import com.wescrum.scrumvy.entity.Invite;
import com.wescrum.scrumvy.entity.ProjectTeam;
import com.wescrum.scrumvy.entity.Role;
import com.wescrum.scrumvy.entity.User;
import com.wescrum.scrumvy.repos.ProjectTeamRepository;
import com.wescrum.scrumvy.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private ProjectTeamRepository projectTeamRepo;

    @Override
    @Transactional
    public User findByUserName(String userName) {
        // checks the database if a user exists in it
        return userDao.findByUserName(userName);
    }

    @Override
    @Transactional
    public User findByUserId(Integer userId) {
        return userRepository.getOne(userId);
    }

    @Override
    @Transactional
    public void save(UserDto userDto) {
        User user = new User();
        // assign user dto details to the user object. premium is not needed default value is false
        user.setUsername(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        // default role is employee . for admin we give manual approve directly from db.
        user.setRoleCollection(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));
        // save user in the database
        userDao.save(user);
    }

    @Override
    @Transactional
    public void saveUserWithProject(User user) {
        userRepository.save(user);
    }

    @Override
    public User getLoggedinUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        User user = findByUserName(username);
        return user;
    }

    @Override
    public boolean checkIfUserIsPartOfAProject(Invite invite) {
        List<ProjectTeam> checkProjectMembers = projectTeamRepo.findByProjectId(invite.getProjectId());
        boolean toggle = false;
        for (ProjectTeam pt : checkProjectMembers) {
            if ((pt.getUserId()== invite.getReceivingUserId())) {
                toggle = true;
            }
        }
        return toggle;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoleCollection()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
