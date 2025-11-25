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

    public Project getById(int id){

    }

    public void addProject(Project Model){

    }

    public void delete(int id){

    }

    public List<Project> getAllProjectsByUser (int "project leader" id){

    }


}
