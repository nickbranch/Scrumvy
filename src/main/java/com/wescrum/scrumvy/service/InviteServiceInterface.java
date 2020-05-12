package com.wescrum.scrumvy.service;

import com.wescrum.scrumvy.entity.Invite;
import java.util.List;

public interface InviteServiceInterface {

    public List<Invite> getAllInvites();

    public void createInvite(Invite invite);

    public Invite getInviteById(Long id);

    public void deleteInvite(Invite invite);

    //public void editInvite(Invite invite);
}
