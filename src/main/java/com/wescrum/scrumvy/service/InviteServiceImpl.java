package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.controller.ProjectController;
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

    @Override
    public boolean acceptInviteLogicCheck(Invite invite) {
        boolean logicErrorTrigger = false;
        //logic should check if the invite id belongs to the collection of invite ids of the project
        //protect against hidden field form data tampering
        List<Invite> getProjectsInvites = inviteRepo.findByProjectId(invite.getProjectId());
        for (Invite projectsInvite : getProjectsInvites) {
            if (projectsInvite.getInviteId() == invite.getInviteId()) {
                logicErrorTrigger = true;
            }
        }
        // logic should check the active user
        if (invite.getReceivingUserId().getId() == ProjectController.activeUser) {
            logicErrorTrigger = true;
        }
        // check if invite accepted status is still false
        if (invite.getAccepted() == false) {
            logicErrorTrigger = true;
        }
        return logicErrorTrigger;
    }

    @Override
    public void performClearAfterAccept(Invite invite) {
        List<Invite> cleanUpList = inviteRepo.findByProjectId(invite.getProjectId());
        User toHaveInvitesDeleted = invite.getReceivingUserId();
        for (Invite invForDel : cleanUpList) {
            if ((invForDel.getProjectRoleId().getProjectRoleId() == 2)
                    || (invForDel.getReceivingUserId() == invite.getReceivingUserId())) {
                inviteRepo.delete(invForDel);
            }
        }
    }
}
