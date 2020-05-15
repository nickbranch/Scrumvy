package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Invite;
import com.wescrum.scrumvy.entity.Project;
import com.wescrum.scrumvy.entity.User;
import com.wescrum.scrumvy.repos.InviteRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InviteServiceImpl implements InviteServiceInterface {

    @Autowired
    private InviteRepository inviteRepo;

    @Autowired
    private ProjectServiceInterface projectService;

    @Override
    public List<Invite> getAllInvites() {
        return inviteRepo.findAll();
    }

    @Override
    public void createInvite(Invite invite) {
        inviteRepo.save(invite);
    }

    @Override
    public Invite getInviteById(Long id) {
        return inviteRepo.getOne(id);
    }

    @Override
    public void deleteInvite(Invite invite) {
        inviteRepo.delete(invite);
    }

    @Override
    public List<Invite> getSentInvites(User user) {
        List<Project> usersProjects = projectService.getAllOwnedProjectsOfAUser(user.getId()); //owned projects
        List<Invite> projectInvites = new ArrayList();  // to be returned
        usersProjects.forEach((usersProject) -> {
            projectInvites.addAll(usersProject.getInviteCollection());
        });
        return projectInvites;
    }

    @Override
    public boolean checkForDuplicate(Invite invite) {
        List<Invite> checkReceivingUserInvites = inviteRepo.findByReceivingUserId(invite.getReceivingUserId());
        boolean toggle = false;
        for (Invite inv : checkReceivingUserInvites) {
            if ((inv.getReceivingUserId() == invite.getReceivingUserId())
                    && (inv.getProjectRoleId() == invite.getProjectRoleId())) {
                toggle = true;
            }
        }
        return toggle;
    }
}
