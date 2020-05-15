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
@Table(name = "project_team")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectTeam.findAll", query = "SELECT p FROM ProjectTeam p"),
    @NamedQuery(name = "ProjectTeam.findByProjectTeamId", query = "SELECT p FROM ProjectTeam p WHERE p.projectTeamId = :projectTeamId")})
public class ProjectTeam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "project_team_id")
    private Long projectTeamId;
    
    @JoinColumn(name = "project_role_id", referencedColumnName = "project_role_id")
    @ManyToOne(optional = false)
    private ProjectRole projectRoleId;
    
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    @ManyToOne(optional = false)
    private Project projectId;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public ProjectTeam(ProjectRole projectRoleId, Project projectId, User userId) {
        this.projectRoleId = projectRoleId;
        this.projectId = projectId;
        this.userId = userId;
    }

    public ProjectTeam() {
    }

    public ProjectTeam(Long projectTeamId) {
        this.projectTeamId = projectTeamId;
    }

    public Long getProjectTeamId() {
        return projectTeamId;
    }

    public void setProjectTeamId(Long projectTeamId) {
        this.projectTeamId = projectTeamId;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
