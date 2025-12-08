package org.example.eksamenprojekt.Repository;

import org.example.eksamenprojekt.Model.Status;
import org.example.eksamenprojekt.Model.SubTask;
import org.example.eksamenprojekt.Model.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class SubTaskRepo {
    private final JdbcTemplate jdbcTemplate;

    public SubTaskRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    private RowMapper<SubTask> subTaskRowMapper = (rs, rowNum) ->
            new SubTask(
                    rs.getInt("Task_Id"),
                    rs.getInt("Subtask_Id"),
                    rs.getInt("Employee_Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getDouble("EstimatedHours"),
                    Status.valueOf(rs.getString("Status").toUpperCase())
            );

    public List<SubTask> findAll(){
    String sql = "Select * from Subtask";
    return jdbcTemplate.query(sql, subTaskRowMapper);
    }

    public int update(int subTaskId, SubTask updated){
    String sql = "update Subtask set Name=?, Description=?, EstimatedHours=?, Status=? where Subtask_Id=?";
    return jdbcTemplate.update(sql,
            updated.getName(),
            updated.getDescription(),
            updated.getEstimatedHours(),
            updated.getStatus(),
            subTaskId
        );
    }

    public int delete(int subTaskId){
    String sql = "delete from Subtask where Subtask_Id=?";
    return jdbcTemplate.update(sql, subTaskId);
    }

    // Subtask_Id, Employee_Id
    public int save(SubTask subTask) {
        String sql = "insert into Subtask (Task_Id, Name, Description, EstimatedHours, Status) values (?,?,?,?,?)";
        return jdbcTemplate.update(sql,
                subTask.getTaskId(),
                subTask.getName(),
                subTask.getDescription(),
                subTask.getEstimatedHours(),
                subTask.getStatus()
        );
    }

    public List<SubTask> findAllByUserID(int userId) {
        String sql = "select * from Subtask where Task_Id in (select Task_Id from EmployeeTask where Employee_Id=?)";


        return jdbcTemplate.query(sql,(rs, rowNum) -> {
            SubTask st = new SubTask();
            st.setSubTaskId(rs.getInt("Id"));
            st.setTaskId(rs.getInt("Task_Id"));
            st.setName(rs.getString("Name"));
            st.setDescription(rs.getString("Description"));
            st.setEstimatedHours(rs.getDouble("EstimatedHours"));
            st.setStatus(Status.valueOf(rs.getString("Status").toUpperCase()));
            return st;
        }, userId);
    }

    public SubTask findSubTaskBySubTaskId(int subTaskId){
    String sql = "select * from Subtask where Subtask_Id=?";
    return jdbcTemplate.queryForObject(sql, subTaskRowMapper);
    }

    public List<SubTask> getAllSubTasksByTaskID(int subtaskId){
        String sql ="select * from Subtask where Task_Id =?";
        return jdbcTemplate.query(sql, subTaskRowMapper, subtaskId);
    }
}
