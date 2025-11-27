package org.example.eksamenprojekt.Repository;

import org.example.eksamenprojekt.Model.Status;
import org.example.eksamenprojekt.Model.SubTask;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class SubTaskRepo {
    private final JdbcTemplate jdbcTemplate;

    public SubTaskRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    private RowMapper<SubTask> subTaskRowMapper = (rs, rowNum) ->
            new SubTask(
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

    public int save(SubTask subTask) {
        String sql = "insert into Subtask (Subtask_Id, Employee_Id, Name, Description, EstimatedHours, Status) values (?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,
                subTask.getSubTaskId(),
                subTask.getUserId(),
                subTask.getName(),
                subTask.getDescription(),
                subTask.getEstimatedHours(),
                subTask.getStatus()
        );
    }

    public List<SubTask> findAllByUserID(int userId) {
        String sql = "select * from Subtask where Employee_Id=?";
        RowMapper<SubTask> rowMapper = (rs, rowNum) -> {
            SubTask st = new SubTask();
            st.setSubTaskId(rs.getInt("Subtask_Id"));
            st.setUserId(rs.getInt("Employee_Id"));
            st.setName(rs.getString("Name"));
            st.setDescription(rs.getString("Description"));
            st.setEstimatedHours(rs.getDouble("EstimatedHours"));
            st.setStatus(Status.valueOf(rs.getString("Status").toUpperCase()));
            return st;
        };
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public SubTask findSubTaskBySubTaskId(int subTaskId){
    String sql = "select * from Subtask where Subtask_Id=?";
    return jdbcTemplate.queryForObject(sql, subTaskRowMapper);
    }

}
