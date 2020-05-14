package com.wescrum.scrumvy.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "projects")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p"),
    @NamedQuery(name = "Project.findByProjectId", query = "SELECT p FROM Project p WHERE p.projectId = :projectId"),
    @NamedQuery(name = "Project.findByProjectName", query = "SELECT p FROM Project p WHERE p.projectName = :projectName"),
    @NamedQuery(name = "Project.findByProjectDescription", query = "SELECT p FROM Project p WHERE p.projectDescription = :projectDescription"),
    @NamedQuery(name = "Project.findByStartDate", query = "SELECT p FROM Project p WHERE p.startDate = :startDate"),
    @NamedQuery(name = "Project.findByEndDate", query = "SELECT p FROM Project p WHERE p.endDate = :endDate")})
public class Project implements Serializable {

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

//    @ManyToMany(mappedBy = "projectsCollection")
    @ManyToMany
    @JoinTable(name = "user_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Collection<User> userCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<ProjectTeam> projectTeamCollection;

    @OneToMany(mappedBy = "projectId")
    private Collection<Retrospective> retrospectiveCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Invite> inviteCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Sprint> sprintCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<DailyScrumRecord> dailyScrumRecordCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectId")
    private Collection<Task> taskCollection;

    public Project() {
    }

    public Project(Long projectId) {
        this.projectId = projectId;
    }

    public Project(Long projectId, String projectName, String projectDescription) {
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

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @XmlTransient
    public Collection<ProjectTeam> getProjectTeamCollection() {
        return projectTeamCollection;
    }

    public void setProjectTeamCollection(Collection<ProjectTeam> projectTeamCollection) {
        this.projectTeamCollection = projectTeamCollection;
    }

    @XmlTransient
    public Collection<Retrospective> getRetrospectiveCollection() {
        return retrospectiveCollection;
    }

    public void setRetrospectiveCollection(Collection<Retrospective> retrospectiveCollection) {
        this.retrospectiveCollection = retrospectiveCollection;
    }

    @XmlTransient
    public Collection<Invite> getInviteCollection() {
        return inviteCollection;
    }

    public void setInviteCollection(Collection<Invite> inviteCollection) {
        this.inviteCollection = inviteCollection;
    }

    @XmlTransient
    public Collection<Sprint> getSprintCollection() {
        return sprintCollection;
    }

    public void setSprintCollection(Collection<Sprint> sprintCollection) {
        this.sprintCollection = sprintCollection;
    }

    @XmlTransient
    public Collection<DailyScrumRecord> getDailyScrumRecordCollection() {
        return dailyScrumRecordCollection;
    }

    public void setDailyScrumRecordCollection(Collection<DailyScrumRecord> dailyScrumRecordCollection) {
        this.dailyScrumRecordCollection = dailyScrumRecordCollection;
    }

    @XmlTransient
    public Collection<Task> getTaskCollection() {
        return taskCollection;
    }

    public void setTaskCollection(Collection<Task> taskCollection) {
        this.taskCollection = taskCollection;
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
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.projectId == null && other.projectId != null) || (this.projectId != null && !this.projectId.equals(other.projectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.wescrum.scrumvy.entity.Project[ projectId=" + projectId + " ]";
    }

}
