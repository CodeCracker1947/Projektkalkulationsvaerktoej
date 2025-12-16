package org.example.eksamenprojekt;

import org.example.eksamenprojekt.Model.Project;
import org.example.eksamenprojekt.Repository.ProjectRepo;
import org.example.eksamenprojekt.Service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
public class ProjectRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private ProjectRepo projectRepo;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        projectRepo = new ProjectRepo(jdbcTemplate);
    }

    @Test
    void findAll_returnsProjects() {
        Project p1 = new Project(1, 5, "Project1", "description", "2025-12-20", 40);
        Project p2 = new Project(2, 13, "Project2", "Description", "2026-01-06", 55);

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of(p1, p2));

        List<Project> result = projectRepo.findAll();

        assertEquals(2, result.size());
        assertEquals("Project1", result.get(0).getName());
        verify(jdbcTemplate).query(eq("select * from Project"), any(RowMapper.class));
    }

    @Test
    void save_projectAndReturnId() {
        Project project = new Project(0,10, "NewProject", "descriptions", "2025-10-13", 14);

        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any())).thenReturn(1);

        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class))).thenReturn(42);

        int id = projectRepo.save(project);

        assertEquals(42, id);
        verify(jdbcTemplate).update(
                eq("insert into Project (Employee_Id, Name, Description, Deadline, EstimatedHours) values (?,?,?,?,?)"),
                eq(project.getUserId()),
                eq(project.getName()),
                eq(project.getDescription()),
                eq(project.getDeadline()),
                eq(project.getEstimatedHour())
        );
    }

    @Test
    void update_updatesProject() {
        Project updated = new Project();
        updated.setName("Updated");
        updated.setDescription("New Description");
        updated.setDeadline("2025-11-27");
        updated.setEstimatedHour(23);

        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any())).thenReturn(1);

        int result = projectRepo.update(5, updated);

        assertEquals(1, result);
        verify(jdbcTemplate).update(
                eq("update Project set Name=?, Description=?, Deadline=?, EstimatedHours=? where Id=?"),
                eq("Updated"),
                eq("New Description"),
                eq("2025-11-27"),
                eq(23.0),
                eq(5)
        );
    }

    @Test
    void delete_deletesProject() {
        when(jdbcTemplate.update(anyString(), any(RowMapper.class))).thenReturn(1);

        int result = projectRepo.delete(3);

        assertEquals(0, result);
        verify(jdbcTemplate).update(
                eq("delete from Project where Id=?"),
                eq(3)
        );
    }

    @Test
    void findAllByUserID_returnsList() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(10)))
                .thenReturn(List.of(
                        new Project(1, 10, "P1", "D1", "2025-01-01", 5),
                        new Project(2, 10, "P2", "D2", "2025-02-01", 15)
                ));

        List<Project> result = projectRepo.findAllByUserID(10);

        assertEquals(2, result.size());
        verify(jdbcTemplate).query(
                eq("select * from Project where Id in(select project_Id from EmployeeProject where Employee_Id=?)"),
                any(RowMapper.class),
                eq(10)
        );
    }



}
