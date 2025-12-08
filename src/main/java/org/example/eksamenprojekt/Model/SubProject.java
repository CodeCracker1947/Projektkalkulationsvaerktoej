package org.example.eksamenprojekt.Model;

import java.util.Date;

public class SubProject {
    private String name;
    private String description;
    private int userId;
    private int projectId;
    private int subProjectId;
    private String deadline;
    private double estimatedHour;

    public SubProject(int projectId, int subProjectId, int userId, String name, String description, String deadline, double estimatedHour){
        this.projectId = projectId;
        this.subProjectId = subProjectId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.estimatedHour = estimatedHour;
    }

    public SubProject(String name, String description, String deadline, double estimatedHour){
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.estimatedHour = estimatedHour;
    }

    public SubProject(){
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

    public int getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(int SubProjectId) {
        this.subProjectId = SubProjectId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public double getEstimatedHour() {
        return estimatedHour;
    }

    public void setEstimatedHour(double estimatedHour) {
        this.estimatedHour = estimatedHour;
    }
}
