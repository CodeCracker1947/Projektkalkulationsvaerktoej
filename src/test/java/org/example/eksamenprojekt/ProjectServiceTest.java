package org.example.eksamenprojekt;

import org.example.eksamenprojekt.Model.Project;
import org.example.eksamenprojekt.Repository.ProjectRepo;
import org.example.eksamenprojekt.Service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {

    private ProjectRepo projectRepo;
    private ProjectService projectService;



    @BeforeEach
    void setUp() {
        projectRepo = mock(ProjectRepo.class);
        projectService = new ProjectService(projectRepo);
    }

    @Test
    void createProject_success() {
        Project project = new Project(1, 13, "Test Project", "Description", "2025-10-12", 5);

        Project result = projectService.addProject(project);

        verify(projectRepo).save(project);
        assertEquals(project, result);
    }

    @Test
    void getAll_returnsAllProjects() {
        List<Project> projects = List.of(
                new Project(1, 1, "project1", "description1", "2025-10-10", 10),
                new Project(2, 1, "Project2", "Description2", "2025-01-04", 13)
        );

        when(projectRepo.findAll()).thenReturn(projects);

        List<Project> result = projectService.getAll();

        assertEquals(2, result.size());
        verify(projectRepo).findAll();
    }

    @Test
    void getProjectByProjectId_returnsproject() {
        Project project = new Project(4, 2, "projectTest", "description", "2020-05-03", 4);

        when(projectRepo.findProjectByProjectId(4)).thenReturn(project);

        Project result = projectService.getByProjectId(4);

        assertEquals("projectTest", result.getName());
        verify(projectRepo).findProjectByProjectId(4);
    }

    @Test
    void updateProject_callsRepositoryUpdate() {
        Project updated = new Project(1, 1, "Updated", "Desc", "2025-04-01", 12);

        projectService.updateProject(1, updated);

        verify(projectRepo).update(1, updated);
    }
}
