package org.example.eksamenprojekt.Repository;

import org.example.eksamenprojekt.Model.SubTask;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class SubTaskRepo {
    private final JdbcTemplate jdbcTemplate;

    public SubTaskRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SubTask> findAll(){

    }

    public int update(int subTaskId, SubTask updated){

    }

    public int delete(int subTaskId){

    }

    public int save(SubTask subTask){

    }

    public SubTask findAllByUserID(int userId){

    }

    public SubTask findSubTaskBySubTaskId(int subTaskId){

    }

}
