package org.example.eksamenprojekt.Service;

import org.example.eksamenprojekt.Model.Task;
import org.example.eksamenprojekt.Repository.TaskRepo;

import java.util.List;

public class TaskService {
    private final TaskRepo repository;

    public TaskService(TaskRepo repository){
        this.repository = repository;
    }

    public List<Task> getAll(){

    }

    public Task getByTaskId(int taskId){

    }

    public void addTask(Task Model){

    }

    public Task updateTask(int taskId, Task updated){

    }

    public void delete(int taskId){

    }

    public List<Task> getAllTaskByUserId (int userId){

    }
}
