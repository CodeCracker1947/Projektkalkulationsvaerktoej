package org.example.eksamenprojekt.Repository;


import org.example.eksamenprojekt.Model.SubProject;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SubProjectRepo {
    private final JdbcTemplate jdbcTemplate;

    public SubProjectRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SubProject> findAll(){

    }

    public int update(int id, SubProject updated){

    }

    public int delete(int subProjectId){

    }

    public int save(SubProject subProject){

    }

    public SubProject findAllByUserID(int userId){

    }

    public SubProject findSubProjectBySubProjectId(int subProjectId){

    }


}
