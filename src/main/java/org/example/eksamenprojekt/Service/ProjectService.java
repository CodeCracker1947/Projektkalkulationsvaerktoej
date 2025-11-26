package org.example.eksamenprojekt.Service;

import org.example.eksamenprojekt.Model.Project;
import org.example.eksamenprojekt.Repository.ProjectRepo;

import java.util.List;

public class ProjectService {
    private final ProjectRepo repository;

    public ProjectService(ProjectRepo repository){
        this.repository = repository;
    }

    public List<Project> getAll(){

    }

    public Project getByProjectId(int projectId){

    }

    public void addProject(Project Model){

    }

    public Project updateProject(int projectId, Project updated){

    }

    public void delete(int projectId){

    }

    public List<Project> getAllProjectsByUserId (int userId){

    }


}
