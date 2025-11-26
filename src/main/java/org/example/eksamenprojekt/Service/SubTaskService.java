package org.example.eksamenprojekt.Service;

import org.example.eksamenprojekt.Model.SubTask;
import org.example.eksamenprojekt.Repository.SubTaskRepo;

import java.util.List;

public class SubTaskService {
    private final SubTaskRepo repository;

    public SubTaskService(SubTaskRepo repository){
        this.repository = repository;
    }
    public List<SubTask> getAll(){

    }

    public SubTask getBySubTaskId(int subTaskId){

    }

    public void addSubTask(SubTask Model){

    }

    public SubTask updateSubTask(int subTaskId, SubTask updated){

    }

    public void delete(int subTaskId){

    }

    public List<SubTask> getAllSubTaskByUserId (int userId){

    }

}
