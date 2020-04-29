/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author nklad
 */
@Entity
@Table(name = "invites")
@NamedQueries({
    @NamedQuery(name = "Invites.findAll", query = "SELECT i FROM Invites i"),
    @NamedQuery(name = "Invites.findByInviteId", query = "SELECT i FROM Invites i WHERE i.inviteId = :inviteId"),
    @NamedQuery(name = "Invites.findByAccepted", query = "SELECT i FROM Invites i WHERE i.accepted = :accepted")})
public class Invites implements Serializable {

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
    private ProjectRoles projectRoleId;
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    @ManyToOne(optional = false)
    private Projects projectId;
    @JoinColumn(name = "receiving_user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User receivingUserId;

    public Invites() {
    }

    public Invites(Long inviteId) {
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

    public ProjectRoles getProjectRoleId() {
        return projectRoleId;
    }

    public void setProjectRoleId(ProjectRoles projectRoleId) {
        this.projectRoleId = projectRoleId;
    }

    public Projects getProjectId() {
        return projectId;
    }

    public void setProjectId(Projects projectId) {
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
        if (!(object instanceof Invites)) {
            return false;
        }
        Invites other = (Invites) object;
        if ((this.inviteId == null && other.inviteId != null) || (this.inviteId != null && !this.inviteId.equals(other.inviteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wescrum.scrumvy.entity.Invites[ inviteId=" + inviteId + " ]";
    }
    
}
