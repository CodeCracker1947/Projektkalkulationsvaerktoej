package org.example.eksamenprojekt.Service;

import org.example.eksamenprojekt.Model.SubProject;
import org.example.eksamenprojekt.Repository.SubProjectRepo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SubProjectService {
    private final SubProjectRepo repository;

    public SubProjectService(SubProjectRepo repository){
        this.repository = repository;
    }

    public List<SubProject> getAll(){
    return repository.findAll();
    }

    public List <SubProject> getAllSubProjectsByProjectId(int projectId){
    return repository.getAllSubProjectByProjectId(projectId);
    }

    public SubProject getBySubProjectId(int subProjectId){
        return repository.findSubProjectBySubProjectId(subProjectId);
    }

   public void addSubProject(SubProject subProject){
    repository.save(subProject);
    }

    public void updateSubProject(int subProjectId, SubProject updated){
    repository.update(subProjectId, updated);
    }

    public void delete(int subProjectId){
    repository.delete(subProjectId);
    }

    /*public List<SubProject> getAllSubProjectsByUserId (int userId){
    return repository.findAllByUserID(userId);
    }

     */
}
