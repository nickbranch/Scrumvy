package com.wescrum.scrumvy.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "invites")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invite.findAll", query = "SELECT i FROM Invite i"),
    @NamedQuery(name = "Invite.findByInviteId", query = "SELECT i FROM Invite i WHERE i.inviteId = :inviteId"),
    @NamedQuery(name = "Invite.findByAccepted", query = "SELECT i FROM Invite i WHERE i.accepted = :accepted")})
public class Invite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "invite_id")
    private Long inviteId;

    @Column(name = "accepted")
    private Boolean accepted;

    @JoinColumn(name = "project_role_id", referencedColumnName = "project_role_id")
    @ManyToOne(optional = false)
    private ProjectRole projectRoleId;

    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    @ManyToOne(optional = false)
    private Project projectId;

    @JoinColumn(name = "receiving_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User receivingUserId;

    public Invite() {
    }

    public Invite(Long inviteId) {
        this.inviteId = inviteId;
    }

    public Long getInviteId() {
        return inviteId;
    }

    public void setInviteId(Long inviteId) {
        this.inviteId = inviteId;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public ProjectRole getProjectRoleId() {
        return projectRoleId;
    }

    public void setProjectRoleId(ProjectRole projectRoleId) {
        this.projectRoleId = projectRoleId;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    public User getReceivingUserId() {
        return receivingUserId;
    }

    public void setReceivingUserId(User receivingUserId) {
        this.receivingUserId = receivingUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inviteId != null ? inviteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invite)) {
            return false;
        }
        Invite other = (Invite) object;
        if ((this.inviteId == null && other.inviteId != null) || (this.inviteId != null && !this.inviteId.equals(other.inviteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wescrum.scrumvy.entity.Invite[ inviteId=" + inviteId + " ]";
    }

}
