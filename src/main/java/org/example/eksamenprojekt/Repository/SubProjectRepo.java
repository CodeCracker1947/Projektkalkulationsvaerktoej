package org.example.eksamenprojekt.Repository;


import org.example.eksamenprojekt.Model.SubProject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class SubProjectRepo {
    private final JdbcTemplate jdbcTemplate;

    public SubProjectRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<SubProject> subProjectRowMapper = (rs, rowNum) ->
            new SubProject(
                    rs.getInt("Project_Id"),
                    rs.getInt("Id"),
                    0,
                    rs.getString("Name"),
                    rs.getString("Description"),
                    null,
                    rs.getDouble("EstimatedHours")
            );

    public List<SubProject> findAll(){
    String sql = "select * from Subproject";
    return jdbcTemplate.query(sql, subProjectRowMapper);
    }

    public int update(int subProjectId, SubProject updated){
        String sql = "update Subproject set name=?, Description=?, EstimatedHours =? where Id=?";
        return jdbcTemplate.update(sql,
                updated.getName(),
                updated.getDescription(),
                updated.getEstimatedHour(),
                subProjectId
        );
    }

    public int delete(int subProjectId){
    String sql = "delete from Subproject where Id=?";
    return jdbcTemplate.update(sql, subProjectId);
    }

    public int save(SubProject subProject){
    String sql = "insert into Subproject (Project_Id, Name, Description, EstimatedHours) values (?,?,?,?)";
    return jdbcTemplate.update(sql,
            subProject.getProjectId(),
            subProject.getName(),
            subProject.getDescription(),
            subProject.getEstimatedHour()
        );
    }

    public List<SubProject> findAllByUserID(int userId){
        String sql = "select * from Subproject where Project_Id in (Select Project_Id from EmployeeProject where Employee_Id=?)";


        return jdbcTemplate.query(sql,(rs, rowNum) -> {
            SubProject sp = new SubProject();
                    sp.setSubProjectId(rs.getInt("Id"));
                    sp.setProjectId(rs.getInt("Project_Id"));
                    sp.setName(rs.getString("Name"));
                    sp.setDescription(rs.getString("Description"));
                    sp.setEstimatedHour(rs.getDouble("EstimatedHours"));
                    return sp;
        }, userId);
    }

    public SubProject findSubProjectBySubProjectId(int subProjectId){
      String sql = "select * from Subproject where Id=?";
      return jdbcTemplate.queryForObject(sql, subProjectRowMapper,subProjectId);
    }


    public List<SubProject> getAllSubProjectByProjectId(int projectId){
    String sql = "select * from Subproject where Project_Id=?";
    return jdbcTemplate.query(sql, subProjectRowMapper, projectId);
    }


}
