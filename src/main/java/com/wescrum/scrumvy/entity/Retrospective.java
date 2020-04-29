/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wescrum.scrumvy.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author nklad
 */
@Entity
@Table(name = "retrospective")
@NamedQueries({
    @NamedQuery(name = "Retrospective.findAll", query = "SELECT r FROM Retrospective r"),
    @NamedQuery(name = "Retrospective.findByStoryId", query = "SELECT r FROM Retrospective r WHERE r.storyId = :storyId"),
    @NamedQuery(name = "Retrospective.findByDescription", query = "SELECT r FROM Retrospective r WHERE r.description = :description"),
    @NamedQuery(name = "Retrospective.findByTimestamp", query = "SELECT r FROM Retrospective r WHERE r.timestamp = :timestamp")})
public class Retrospective implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "story_id")
    private Long storyId;
    @Size(max = 150)
    @Column(name = "description")
    private String description;
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    @ManyToOne
    private Projects projectId;

    public Retrospective() {
    }

    public Retrospective(Long storyId) {
        this.storyId = storyId;
    }

    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
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
        hash += (storyId != null ? storyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Retrospective)) {
            return false;
        }
        Retrospective other = (Retrospective) object;
        if ((this.storyId == null && other.storyId != null) || (this.storyId != null && !this.storyId.equals(other.storyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wescrum.scrumvy.entity.Retrospective[ storyId=" + storyId + " ]";
    }
    
}
