package org.example.eksamenprojekt.Model;

public class SubTask {
    private String name;
    private int taskId;
    private int subTaskId;
    private String description;
    private double estimatedHours;
    private Status status;

    public SubTask(int taskId, int subTaskId, String name, String description, double estimatedHours, Status status){
        this.taskId = taskId;
        this.subTaskId = subTaskId;
        this.name = name;
        this.description = description;
        this.estimatedHours = estimatedHours;
        this.status = status;
    }

    public SubTask(String name, String description, double estimatedHours, Status status){
        this.name = name;
        this.description = description;
        this.estimatedHours = estimatedHours;
        this.status = status;
    }

    public SubTask(){
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

    public int getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
