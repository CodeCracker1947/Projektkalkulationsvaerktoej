package org.example.eksamenprojekt.Service;

import org.example.eksamenprojekt.Model.SubProject;
import org.example.eksamenprojekt.Repository.SubProjectRepo;

import java.util.List;

public class SubProjectService {
    private final SubProjectRepo repository;

    public SubProjectService(SubProjectRepo repository){
        this.repository = repository;
    }

    public List<SubProject> getAll(){
    return repository.findAll();
    }

    public SubProject getBySubProjectId(int subProjectId){
    return repository.findSubProjectBySubProjectId(subProjectId);
    }

    public void addSubProject(SubProject Model){
    repository.save(Model);
    }

    public void updateSubProject(int subProjectId, SubProject updated){
    repository.update(subProjectId, updated);
    }

    public void delete(int subProjectId){
    repository.delete(subProjectId);
    }

    public List<SubProject> getAllSubProjectsByUserId (int userId){
    return repository.findAllByUserID(userId);
    }
}
