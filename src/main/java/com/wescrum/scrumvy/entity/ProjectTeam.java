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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author nklad
 */
@Entity
@Table(name = "project_team")
@NamedQueries({
    @NamedQuery(name = "ProjectTeam.findAll", query = "SELECT p FROM ProjectTeam p"),
    @NamedQuery(name = "ProjectTeam.findByProjectTeamId", query = "SELECT p FROM ProjectTeam p WHERE p.projectTeamId = :projectTeamId")})
public class ProjectTeam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "project_team_id")
    private Integer projectTeamId;
    @JoinColumn(name = "project_role_id", referencedColumnName = "project_role_id")
    @ManyToOne(optional = false)
    private ProjectRoles projectRoleId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectTeamId")
    private Collection<Projects> projectsCollection;

    public ProjectTeam() {
    }

    public ProjectTeam(Integer projectTeamId) {
        this.projectTeamId = projectTeamId;
    }

    public Integer getProjectTeamId() {
        return projectTeamId;
    }

    public void setProjectTeamId(Integer projectTeamId) {
        this.projectTeamId = projectTeamId;
    }

    public ProjectRoles getProjectRoleId() {
        return projectRoleId;
    }

    public void setProjectRoleId(ProjectRoles projectRoleId) {
        this.projectRoleId = projectRoleId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Collection<Projects> getProjectsCollection() {
        return projectsCollection;
    }

    public void setProjectsCollection(Collection<Projects> projectsCollection) {
        this.projectsCollection = projectsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectTeamId != null ? projectTeamId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProjectTeam)) {
            return false;
        }
        ProjectTeam other = (ProjectTeam) object;
        if ((this.projectTeamId == null && other.projectTeamId != null) || (this.projectTeamId != null && !this.projectTeamId.equals(other.projectTeamId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wescrum.scrumvy.entity.ProjectTeam[ projectTeamId=" + projectTeamId + " ]";
    }
    
}
