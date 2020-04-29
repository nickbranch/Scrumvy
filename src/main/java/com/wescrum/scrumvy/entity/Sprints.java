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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author nklad
 */
@Entity
@Table(name = "sprints")
@NamedQueries({
    @NamedQuery(name = "Sprints.findAll", query = "SELECT s FROM Sprints s"),
    @NamedQuery(name = "Sprints.findBySprintId", query = "SELECT s FROM Sprints s WHERE s.sprintId = :sprintId"),
    @NamedQuery(name = "Sprints.findBySprintStartDate", query = "SELECT s FROM Sprints s WHERE s.sprintStartDate = :sprintStartDate"),
    @NamedQuery(name = "Sprints.findBySprintEndDate", query = "SELECT s FROM Sprints s WHERE s.sprintEndDate = :sprintEndDate")})
public class Sprints implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sprint_id")
    private Long sprintId;
    @Column(name = "sprint_start_date")
    @Temporal(TemporalType.DATE)
    private Date sprintStartDate;
    @Column(name = "sprint_end_date")
    @Temporal(TemporalType.DATE)
    private Date sprintEndDate;
    @ManyToMany(mappedBy = "sprintsCollection")
    private Collection<Tasks> tasksCollection;
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    @ManyToOne(optional = false)
    private Projects projectId;

    public Sprints() {
    }

    public Sprints(Long sprintId) {
        this.sprintId = sprintId;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprintId) {
        this.sprintId = sprintId;
    }

    public Date getSprintStartDate() {
        return sprintStartDate;
    }

    public void setSprintStartDate(Date sprintStartDate) {
        this.sprintStartDate = sprintStartDate;
    }

    public Date getSprintEndDate() {
        return sprintEndDate;
    }

    public void setSprintEndDate(Date sprintEndDate) {
        this.sprintEndDate = sprintEndDate;
    }

    public Collection<Tasks> getTasksCollection() {
        return tasksCollection;
    }

    public void setTasksCollection(Collection<Tasks> tasksCollection) {
        this.tasksCollection = tasksCollection;
    }

    public Projects getProjectId() {
        return projectId;
    }

    public void setProjectId(Projects projectId) {
        this.projectId = projectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sprintId != null ? sprintId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sprints)) {
            return false;
        }
        Sprints other = (Sprints) object;
        if ((this.sprintId == null && other.sprintId != null) || (this.sprintId != null && !this.sprintId.equals(other.sprintId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wescrum.scrumvy.entity.Sprints[ sprintId=" + sprintId + " ]";
    }
    
}
