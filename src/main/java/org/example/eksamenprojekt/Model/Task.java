package org.example.eksamenprojekt.Model;

import java.util.Date;

public class Task {
    private String name;
    private int taskId;
    private int userId;
    private String description;
    private double estimatedHours;
    private Date deadline;
    private Status status;

    public Task(int taskId, int userId, String name, String description, Date deadline, double estimatedHours, Status status){
        this.taskId = taskId;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.estimatedHours = estimatedHours;
        this.status = status;
    }

    public Task(String name, String description, Date deadline, double estimatedHours, Status status){
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.estimatedHours = estimatedHours;
        this.status = status;
    }

    public Task(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
