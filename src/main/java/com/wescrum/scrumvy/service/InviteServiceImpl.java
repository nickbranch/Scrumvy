package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Invite;
import com.wescrum.scrumvy.repos.InviteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InviteServiceImpl implements InviteServiceInterface {

    @Autowired 
    InviteRepository inviteRepo;
    
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

}
