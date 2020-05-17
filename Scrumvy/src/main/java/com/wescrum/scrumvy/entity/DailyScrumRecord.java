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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nklad
 */
@Entity
@Table(name = "daily_scrum_record")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DailyScrumRecord.findAll", query = "SELECT d FROM DailyScrumRecord d"),
    @NamedQuery(name = "DailyScrumRecord.findByRecordId", query = "SELECT d FROM DailyScrumRecord d WHERE d.recordId = :recordId"),
    @NamedQuery(name = "DailyScrumRecord.findByDescription", query = "SELECT d FROM DailyScrumRecord d WHERE d.description = :description"),
    @NamedQuery(name = "DailyScrumRecord.findByTimestamp", query = "SELECT d FROM DailyScrumRecord d WHERE d.timestamp = :timestamp")})
public class DailyScrumRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "record_id")
    private Long recordId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "description")
    private String description;
    @Column(name = "timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    @ManyToOne(optional = false)
    private Project projectId;

    public DailyScrumRecord() {
    }

    public DailyScrumRecord(Long recordId) {
        this.recordId = recordId;
    }

    public DailyScrumRecord(Long recordId, String description) {
        this.recordId = recordId;
        this.description = description;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
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

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recordId != null ? recordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DailyScrumRecord)) {
            return false;
        }
        DailyScrumRecord other = (DailyScrumRecord) object;
        if ((this.recordId == null && other.recordId != null) || (this.recordId != null && !this.recordId.equals(other.recordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wescrum.scrumvy.entity.DailyScrumRecord[ recordId=" + recordId + " ]";
    }
    
}
