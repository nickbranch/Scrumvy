/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wescrum.scrumvy.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nklad
 */
@Entity
@Table(name = "projects")
@NamedQueries({
    @NamedQuery(name = "Projects.findAll", query = "SELECT p FROM Projects p"),
    @NamedQuery(name = "Projects.findByProjectId", query = "SELECT p FROM Projects p WHERE p.projectId = :projectId"),
    @NamedQuery(name = "Projects.findByProjectName", query = "SELECT p FROM Projects p WHERE p.projectName = :projectName"),
    @NamedQuery(name = "Projects.findByProjectDescription", query = "SELECT p FROM Projects p WHERE p.projectDescription = :projectDescription"),
    @NamedQuery(name = "Projects.findByStartDate", query = "SELECT p FROM Projects p WHERE p.startDate = :startDate"),
    @NamedQuery(name = "Projects.findByEndDate", query = "SELECT p FROM Projects p WHERE p.endDate = :endDate")})
public class Projects implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "project_id")
    private Long projectId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "project_name")
    private String projectName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "project_description")
    private String projectDescription;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @JoinTable(name = "user_project", joinColumns = {
        @JoinColumn(name = "project_id", referencedColumnName = "project_id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_id", referencedColumnName = "id")})
    @ManyToMany
    private Collection<User> userCollection;
    @JoinColumn(name = "project_team_id", referencedColumnName = "project_team_id")
    @ManyToOne(optional = false)
    private ProjectTeam projectTeamId;
    @OneToMany(mappedBy = "projectId")
    private Collection<Retrospective> retrospectiveCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Invites> invitesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Sprints> sprintsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<DailyScrumRecord> dailyScrumRecordCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Tasks> tasksCollection;

    public Projects() {
    }

    public Projects(Long projectId) {
        this.projectId = projectId;
    }

    public Projects(Long projectId, String projectName, String projectDescription) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    public ProjectTeam getProjectTeamId() {
        return projectTeamId;
    }

    public void setProjectTeamId(ProjectTeam projectTeamId) {
        this.projectTeamId = projectTeamId;
    }

    public Collection<Retrospective> getRetrospectiveCollection() {
        return retrospectiveCollection;
    }

    public void setRetrospectiveCollection(Collection<Retrospective> retrospectiveCollection) {
        this.retrospectiveCollection = retrospectiveCollection;
    }

    public Collection<Invites> getInvitesCollection() {
        return invitesCollection;
    }

    public void setInvitesCollection(Collection<Invites> invitesCollection) {
        this.invitesCollection = invitesCollection;
    }

    public Collection<Sprints> getSprintsCollection() {
        return sprintsCollection;
    }

    public void setSprintsCollection(Collection<Sprints> sprintsCollection) {
        this.sprintsCollection = sprintsCollection;
    }

    public Collection<DailyScrumRecord> getDailyScrumRecordCollection() {
        return dailyScrumRecordCollection;
    }

    public void setDailyScrumRecordCollection(Collection<DailyScrumRecord> dailyScrumRecordCollection) {
        this.dailyScrumRecordCollection = dailyScrumRecordCollection;
    }

    public Collection<Tasks> getTasksCollection() {
        return tasksCollection;
    }

    public void setTasksCollection(Collection<Tasks> tasksCollection) {
        this.tasksCollection = tasksCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectId != null ? projectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projects)) {
            return false;
        }
        Projects other = (Projects) object;
        if ((this.projectId == null && other.projectId != null) || (this.projectId != null && !this.projectId.equals(other.projectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wescrum.scrumvy.entity.Projects[ projectId=" + projectId + " ]";
    }
    
}
