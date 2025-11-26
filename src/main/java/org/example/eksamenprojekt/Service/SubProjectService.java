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

    }

    public SubProject getBySubProjectId(int subProjectId){

    }

    public void addSubProject(SubProject Model){

    }

    public SubProject updateSubProject(int subProjectId, SubProject updated){

    }

    public void delete(int subProjectId){

    }

    public List<SubProject> getAllSubProjectsByUserId (int userId){

    }
}
