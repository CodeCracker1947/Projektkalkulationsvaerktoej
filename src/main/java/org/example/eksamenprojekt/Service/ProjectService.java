package org.example.eksamenprojekt.Service;

import org.example.eksamenprojekt.Model.Project;
import org.example.eksamenprojekt.Repository.ProjectRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepo repository;

    public ProjectService(ProjectRepo repository){
        this.repository = repository;
    }

    public List<Project> getAll(){
    return repository.findAll();
    }

    public Project getByProjectId(int projectId){
    return repository.findProjectByProjectId(projectId);
    }

    public void addProject(Project Model){
    repository.save(Model);
    }

    public void updateProject(int projectId, Project updated){
    repository.update(projectId, updated);
    }

    public void delete(int projectId){
    repository.delete(projectId);
    }

    public List<Project> getAllProjectsByUserId (int userId){
    return repository.findAllByUserID(userId);
    }


}
