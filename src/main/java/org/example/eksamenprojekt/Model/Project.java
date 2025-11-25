package org.example.eksamenprojekt.Model;

import java.util.Date;

public class Project {
    private String name;
    private String description;
    private int projectId;
    private Date deadline;
    private Date estimatedTime;

    public Project(int projectId, String name, String description, Date deadline, Date estimatedTime){
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.estimatedTime = estimatedTime;
    }

    public Project(String name, String description, Date deadline, Date estimatedTime){
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.estimatedTime = estimatedTime;
    }

    public Project(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Date estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
}
