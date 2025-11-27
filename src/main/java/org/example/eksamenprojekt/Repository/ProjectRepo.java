package org.example.eksamenprojekt.Repository;

import org.example.eksamenprojekt.Model.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class ProjectRepo {
    private final JdbcTemplate jdbcTemplate;
    public ProjectRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;

    }

    private RowMapper<Project> ProjectRowMapper = (rs, rowNum) ->
            new Project(
                    rs.getInt("Project_Id"),
                    rs.getInt("Employee_Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getDate("Deadline"),
                    rs.getDouble("EstimatedHours")
            );

    public List<Project> findAll(){
        String sql = "select * from Project";
        return jdbcTemplate.query(sql, ProjectRowMapper);

    }

    public int update(int projectId, Project updated){
    String sql = "update Project set Name=?, Description=?, Deadline=?, EstimatedHours=? where Project_Id=?";
    return jdbcTemplate.update(sql,
            updated.getName(),
            updated.getDescription(),
            updated.getDeadline(),
            updated.getEstimatedHour(),
            projectId
        );
    }

    public int delete(int projectId){
    String sql = "delete from Project where Project_Id=?";
    return jdbcTemplate.update(sql, projectId);
    }

    public int save(Project project){
        String sql = "insert into * Project (Project_Id, Employee_Id, Name, Description, Deadline, EstimatedHours) values (?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,
                project.getProjectId(),
                project.getUserId(),
                project.getName(),
                project.getDescription(),
                project.getDeadline(),
                project.getEstimatedHour()
        );

    }

    public List<Project> findAllByUserID(int userId){
        String sql = "select * from Project where Employee_Id =?";
        RowMapper<Project> rowMapper = (rs, rowNum) -> {
            Project p = new Project();
            p.setProjectId(rs.getInt("Project_Id"));
            p.setUserId(rs.getInt("Employee_Id"));
            p.setName(rs.getString("Name"));
            p.setDescription(rs.getString("Description"));
            p.setDeadline(rs.getDate("Deadline"));
            p.setEstimatedHour(rs.getDouble("EstimatedHours"));
            return p;
        };
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    public Project findProjectByProjectId(int projectId){
    String sql = "select * from Project where Project_Id=?";
    return jdbcTemplate.queryForObject(sql, ProjectRowMapper, projectId);
    }
}
