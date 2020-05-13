package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Invite;
import com.wescrum.scrumvy.entity.User;
import java.util.List;

public interface InviteServiceInterface {

    public List<Invite> getAllInvites();

    public void createInvite(Invite invite);

    public Invite getInviteById(Long id);

    public void deleteInvite(Invite invite);

    public List<Invite> getSentInvites(User user);

    public List<Invite> getReceivedInvites(Integer userId);
    //public void editInvite(Invite invite);
}
