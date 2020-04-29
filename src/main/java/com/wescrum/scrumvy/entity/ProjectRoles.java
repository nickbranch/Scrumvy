/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wescrum.scrumvy.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author nklad
 */
@Entity
@Table(name = "project_roles")
@NamedQueries({
    @NamedQuery(name = "ProjectRoles.findAll", query = "SELECT p FROM ProjectRoles p"),
    @NamedQuery(name = "ProjectRoles.findByProjectRoleId", query = "SELECT p FROM ProjectRoles p WHERE p.projectRoleId = :projectRoleId"),
    @NamedQuery(name = "ProjectRoles.findByRoleDescription", query = "SELECT p FROM ProjectRoles p WHERE p.roleDescription = :roleDescription")})
public class ProjectRoles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "project_role_id")
    private Integer projectRoleId;
    @Size(max = 30)
    @Column(name = "role_description")
    private String roleDescription;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectRoleId")
    private Collection<ProjectTeam> projectTeamCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectRoleId")
    private Collection<Invites> invitesCollection;

    public ProjectRoles() {
    }

    public ProjectRoles(Integer projectRoleId) {
        this.projectRoleId = projectRoleId;
    }

    public Integer getProjectRoleId() {
        return projectRoleId;
    }

    public void setProjectRoleId(Integer projectRoleId) {
        this.projectRoleId = projectRoleId;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Collection<ProjectTeam> getProjectTeamCollection() {
        return projectTeamCollection;
    }

    public void setProjectTeamCollection(Collection<ProjectTeam> projectTeamCollection) {
        this.projectTeamCollection = projectTeamCollection;
    }

    public Collection<Invites> getInvitesCollection() {
        return invitesCollection;
    }

    public void setInvitesCollection(Collection<Invites> invitesCollection) {
        this.invitesCollection = invitesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectRoleId != null ? projectRoleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProjectRoles)) {
            return false;
        }
        ProjectRoles other = (ProjectRoles) object;
        if ((this.projectRoleId == null && other.projectRoleId != null) || (this.projectRoleId != null && !this.projectRoleId.equals(other.projectRoleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wescrum.scrumvy.entity.ProjectRoles[ projectRoleId=" + projectRoleId + " ]";
    }
    
}
