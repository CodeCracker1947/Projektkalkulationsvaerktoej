package org.example.eksamenprojekt.Service;

import org.example.eksamenprojekt.Model.SubTask;
import org.example.eksamenprojekt.Repository.SubTaskRepo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubTaskService {
    private final SubTaskRepo repository;

    public SubTaskService(SubTaskRepo repository){
        this.repository = repository;
    }

    public SubTask getBySubTaskId(int subTaskId){
    return repository.findSubTaskBySubTaskId(subTaskId);
    }

    public void addSubTask(SubTask subTask){
    repository.save(subTask);
    }

    public void updateSubTask(int subTaskId, SubTask updated){
    repository.update(subTaskId, updated);
    }

    public void delete(int subTaskId){
    repository.delete(subTaskId);
    }

    public List<SubTask> getAllSubTaskByUserId (int userId){
    return repository.findAllByUserID(userId);
    }

    public List<SubTask> getAllSubTasksByTaskID(int taskId) {
        return repository.getAllSubTasksByTaskID(taskId);
    }
}
