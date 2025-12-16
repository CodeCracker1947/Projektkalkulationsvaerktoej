package org.example.eksamenprojekt.Repository;

import org.example.eksamenprojekt.Model.Status;
import org.example.eksamenprojekt.Model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TaskRepo {
    private final JdbcTemplate jdbcTemplate;

    public TaskRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Task> taskRowMapper = (rs, rowNum) ->
            new Task (
                    rs.getInt("Subproject_Id"),
                    rs.getInt("Id"),
                    0,
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getString("Deadline"),
                    rs.getDouble("EstimatedHours"),
                    Status.valueOf(rs.getString("Status"))
            );

    public List<Task> findAll(){
        String sql = "select * from Task";
        return jdbcTemplate.query(sql, taskRowMapper);

    }

    public int update(int taskId, Task updated){
    String sql = "update Task set Name=?, Description=?, Deadline=?, EstimatedHours=?, Status=? where Id=?";
        return jdbcTemplate.update(sql,
                updated.getName(),
                updated.getDescription(),
                updated.getDeadline(),
                updated.getEstimatedHours(),
                updated.getStatus().name(),
                taskId
        );
    }

    public int delete(int taskId){
        String sql = "delete from Task where Id=?";
        return jdbcTemplate.update(sql, taskId);

    }

    public int save(Task task){
        String sql = "insert into Task (Subproject_Id, Name, Description, Deadline, EstimatedHours, Status) values(?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,
                task.getSubProjectId(),
                task.getName(),
                task.getDescription(),
                task.getDeadline(),
                task.getEstimatedHours(),
                task.getStatus().name()
        );
    }

    public List<Task> findAllByUserID(int userId) {
        String sql = "select * from Task where Id in (select Id from EmployeeTask where Employee_Id=?)";


        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Task t = new Task();
            t.setTaskId(rs.getInt("Id"));
            t.setSubProjectId(rs.getInt("Subproject_Id"));
            t.setName(rs.getString("Name"));
            t.setDescription(rs.getString("Description"));
            t.setDeadline(rs.getString("Deadline"));
            t.setEstimatedHours(rs.getDouble("EstimatedHours"));
            t.setStatus(Status.valueOf(rs.getString("Status").toUpperCase()));
            return t;
        }, userId);
    }

    public Task findTaskByTaskId(int taskId) {
        String sql = "select * from Task where Id=?";
        return jdbcTemplate.queryForObject(sql, taskRowMapper, taskId);
    }

    public List<Task> getAllTaskBySubProjectId(int subprojectId){
        String sql ="select * from Task where Subproject_Id =?";
        return jdbcTemplate.query(sql, taskRowMapper, subprojectId);
    }
}

