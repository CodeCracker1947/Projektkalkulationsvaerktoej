package org.example.eksamenprojekt.Repository;

import org.example.eksamenprojekt.Model.Task;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TaskRepo {
    private final JdbcTemplate jdbcTemplate;

    public TaskRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Task> findAll(){

    }

    public int update(int taskId, Task updated){

    }

    public int delete(int taskId){

    }

    public int save(Task task){

    }

    public Task findAllByUserID(int userId){

    }

    public Task findTaskByTaskId(int taskId){

    }
}
