package org.example.eksamenprojekt.Repository;

import org.example.eksamenprojekt.Model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class ProjectRepo {
    private final JdbcTemplate jdbcTemplate;
    public ProjectRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;

    }

    private RowMapper<Project> ProjectRowMapper = ((rs, rowNum) ->
            new Project())

    public List<Project> findAll(){

    }

    public int update(int projectId, Project updated){

    }

    public int delete(int projectId){

    }

    public int save(Project project){

    }

    public Project findAllByUserID(int userId){

    }

    public Project findProjectByProjectId(int projectId){

    }
}
