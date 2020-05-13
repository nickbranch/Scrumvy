package com.wescrum.scrumvy.repos;

import com.wescrum.scrumvy.entity.Invite;
import com.wescrum.scrumvy.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteRepository extends JpaRepository<Invite, Long> {
    public List<Invite> findByReceivingUserId(User user);
}
