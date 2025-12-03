package org.example.eksamenprojekt.Service;

import org.example.eksamenprojekt.Model.Task;
import org.example.eksamenprojekt.Repository.TaskRepo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskService {
    private final TaskRepo repository;

    public TaskService(TaskRepo repository){
        this.repository = repository;
    }

    public List<Task> getAll(){
    return repository.findAll();
    }

    public Task getByTaskId(int taskId){
    return repository.findTaskByTaskId(taskId);
    }

    public void addTask(Task task){
    repository.save(task);
    }

    public void updateTask(int taskId, Task updated){
    repository.update(taskId, updated);
    }

    public void delete(int taskId){
    repository.delete(taskId);
    }

    public List<Task> getAllTaskByUserId (int userId){
    return repository.findAllByUserID(userId);
    }
}
