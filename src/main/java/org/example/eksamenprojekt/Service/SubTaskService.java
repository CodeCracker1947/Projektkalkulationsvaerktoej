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
    public List<SubTask> getAll(){
    return repository.findAll();
    }

    public SubTask getBySubTaskId(int subTaskId){
    return repository.findSubTaskBySubTaskId(subTaskId);
    }

    public void addSubTask(SubTask Model){
    repository.save(Model);
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

}
