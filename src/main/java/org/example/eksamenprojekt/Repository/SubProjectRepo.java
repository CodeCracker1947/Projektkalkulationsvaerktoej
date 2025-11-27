package org.example.eksamenprojekt.Repository;


import org.example.eksamenprojekt.Model.SubProject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class SubProjectRepo {
    private final JdbcTemplate jdbcTemplate;

    public SubProjectRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<SubProject> subProjectRowMapper = (rs, rowNum) ->
            new SubProject(
                    rs.getInt("Subproject_Id"),
                    rs.getInt("Employee_Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getDate("Deadline"),
                    rs.getDouble("EstimatedHours")
            );

    public List<SubProject> findAll(){
    String sql = "select * from Subproject";
    return jdbcTemplate.query(sql, subProjectRowMapper);
    }

    public int update(int subProjectId, SubProject updated){
        String sql = "update Subproject set name=?, Description=?, Deadline=?, EstimatedHours =? where Subproject_Id=?";
        return jdbcTemplate.update(sql,
                updated.getName(),
                updated.getDescription(),
                updated.getDeadline(),
                updated.getEstimatedHour(),
                subProjectId
        );
    }

    public int delete(int subProjectId){
    String sql = "delete from Subproject where Subproject_Id=?";
    return jdbcTemplate.update(sql, subProjectId);
    }

    public int save(SubProject subProject){
    String sql = "insert into Subproject (Subproject_Id, Employee_ID, Name, Description, Deadline, EstimatedHours) values (?,?,?,?,?,?)";
    return jdbcTemplate.update(sql,
            subProject.getSubProjectId(),
            subProject.getUserId(),
            subProject.getName(),
            subProject.getDescription(),
            subProject.getDeadline(),
            subProject.getEstimatedHour()
        );
    }

    public List<SubProject> findAllByUserID(int userId){
        String sql = "select * from Subproject where Employee_Id=?";
        RowMapper<SubProject> rowMapper = (rs, rowNum) -> {
            SubProject sp = new SubProject();
                    sp.setSubProjectId(rs.getInt("Subproject_Id"));
                    sp.setUserId(rs.getInt("Employee_Id"));
                    sp.setName(rs.getString("Name"));
                    sp.setDescription(rs.getString("Description"));
                    sp.setDeadline(rs.getDate("Deadline"));
                    sp.setEstimatedHour(rs.getDouble("EstimatedHours"));
                    return sp;
        };
        return  jdbcTemplate.query(sql, rowMapper, userId);
    }

    public SubProject findSubProjectBySubProjectId(int subProjectId){
    String sql = "select * Subproject where Subproject_ID=?";
    return jdbcTemplate.queryForObject(sql, subProjectRowMapper, subProjectId);
    }


}
