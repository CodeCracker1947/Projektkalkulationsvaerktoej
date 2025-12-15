package org.example.eksamenprojekt.Repository;

import org.example.eksamenprojekt.Model.Project;
import org.example.eksamenprojekt.Model.Role;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepo {
    private final JdbcTemplate jdbcTemplate;
    public ProjectRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;

    }

    private RowMapper<Project> ProjectRowMapper = (rs, rowNum) ->
            new Project(
                    rs.getInt("Id"),
                    rs.getInt("Employee_Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getString("Deadline"),
                    rs.getDouble("EstimatedHours")
            );

    public List<Project> findAll(){
        String sql = "select * from Project";
        return jdbcTemplate.query(sql, ProjectRowMapper);

    }

    public int update(int projectId, Project updated){
    String sql = "update Project set Name=?, Description=?, Deadline=?, EstimatedHours=? where Id=?";
    return jdbcTemplate.update(sql,
            updated.getName(),
            updated.getDescription(),
            updated.getDeadline(),
            updated.getEstimatedHour(),
            projectId
        );
    }

    public int delete(int projectId){
    String sql = "delete from Project where Id=?";
    return jdbcTemplate.update(sql, projectId);
    }


    public int save(Project project){
        String sql = "insert into Project (Employee_Id,Name, Description, Deadline, EstimatedHours) values (?,?,?,?,?)";
        jdbcTemplate.update(sql,
                project.getUserId(),
                project.getName(),
                project.getDescription(),
                project.getDeadline(),
                project.getEstimatedHour()
        );
        return getLastInsertedProjectId();
    }

    public List<Project> findAllByUserID(int userId){
        String sql = "select * from Project where Id in(select project_Id from EmployeeProject where Employee_Id=?)";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Project p = new Project();
            p.setProjectId(rs.getInt("Id"));
            p.setName(rs.getString("Name"));
            p.setDescription(rs.getString("Description"));
            p.setDeadline(rs.getString("Deadline"));
            p.setEstimatedHour(rs.getDouble("EstimatedHours"));
            return p;
        }, userId);
    }

    public Project findProjectByProjectId(int projectId){
    String sql = "select * from Project where Id=?";
    return jdbcTemplate.queryForObject(sql, ProjectRowMapper, projectId);
    }

    public void assignEmployeeToProject(int projectId, int userId){
        String sql = "insert into EmployeeProject(Employee_Id, Project_Id) values (?,?)";
        jdbcTemplate.update(sql, userId, projectId);
    }

    public int getLastInsertedProjectId(){
        String sql = "SELECT Id FROM Project ORDER BY Id DESC LIMIT 1";
        Integer lastId = jdbcTemplate.queryForObject(sql,Integer.class);
        return lastId != null ? lastId : 0;

    }
}
