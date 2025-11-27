package org.example.eksamenprojekt.Repository;

import org.example.eksamenprojekt.Model.Status;
import org.example.eksamenprojekt.Model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.awt.*;
import java.util.List;
import java.util.Locale;

public class TaskRepo {
    private final JdbcTemplate jdbcTemplate;

    public TaskRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Task> taskRowMapper = (rs, rowNum) ->
            new Task (
                    rs.getInt("Task_Id"),
                    rs.getInt("Employee_Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getDate("Deadline"),
                    rs.getDouble("EstimatedHours"),
                    Status.valueOf(rs.getString("Status").toUpperCase())
            );

    public List<Task> findAll(){
        String sql = "select * from Task";
        return jdbcTemplate.query(sql, taskRowMapper);

    }

    public int update(int taskId, Task updated){
    String sql = "update Task set Name=?, Description=?, Deadline=?, EstimatedHours=?, Status=? where Task_Id=?";
        return jdbcTemplate.update(sql,
                updated.getName(),
                updated.getDescription(),
                updated.getDeadline(),
                updated.getEstimatedHours(),
                updated.getStatus(),
                taskId
        );
    }

    public int delete(int taskId){
        String sql = "delete from Task where Task_Id=?";
        return jdbcTemplate.update(sql, taskId);

    }

    public int save(Task task){
        String sql = "insert into Task (Task_Id, Employee_Id, Name, Description, Deadline, EstimatedHours, Status) values(?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,
                task.getTaskId(),
                task.getUserId(),
                task.getName(),
                task.getDescription(),
                task.getDeadline(),
                task.getEstimatedHours(),
                task.getStatus()
        );
    }

    public List<Task> findAllByUserID(int userId) {
        String sql = "select * from Task where Employee_Id =?";
        RowMapper<Task> rowMapper = (rs, rowNum) -> {
            Task t = new Task();
            t.setTaskId(rs.getInt("Task_Id"));
            t.setUserId(rs.getInt("User_Id"));
            t.setName(rs.getString("name"));
            t.setDescription(rs.getString("Description"));
            t.setDeadline(rs.getDate("Deadline"));
            t.setEstimatedHours(rs.getDouble("EstimatedHours"));
            t.setStatus(Status.valueOf(rs.getString("Status").toUpperCase()));
            return t;
        };
        return jdbcTemplate.query(sql, rowMapper, userId);
    }


    public Task findTaskByTaskId(int taskId) {
        String sql = "select * from Task where Task_Id=?";
        return jdbcTemplate.queryForObject(sql, taskRowMapper, taskId);
    }
}

