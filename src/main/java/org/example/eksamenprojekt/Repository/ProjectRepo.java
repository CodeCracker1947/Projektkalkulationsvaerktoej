package org.example.eksamenprojekt.Repository;

import org.example.eksamenprojekt.Model.Project;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ProjectRepo {
    private final JdbcTemplate jdbcTemplate;
    public ProjectRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;

    }

    public List<Project> findAll(){

    }

    public int update(int id, Project updated){

    }

    public int delete(int id){

    }

    public int save(Project project){

    }

    public Project findProjectById(int id){

    }
}
