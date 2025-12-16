package org.example.eksamenprojekt;

import org.example.eksamenprojekt.Controller.ProjectController;
import org.example.eksamenprojekt.Model.*;
import org.example.eksamenprojekt.Service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private ProjectService projectService;

    @MockitoBean
    private SubProjectService subProjectService;

    @MockitoBean
    private TaskService taskService;

    @MockitoBean
    private SubTaskService subTaskService;

    @Test
    void showProjects_ReturnProjectDetails_whenloggedIn() throws Exception {
        User PropUser = new User();
        PropUser.setName("Filip");

        when(userService.getByUserId(1)).thenReturn(PropUser);
        when(projectService.getAllProjectsByUserId(1)).thenReturn(List.of());

        mockMvc.perform(get("/projects")
                .sessionAttr("userId", 1)
                .sessionAttr("role", Role.DEVELOPER))
                .andExpect(status().isOk())
                .andExpect(view().name("projects"))
                .andExpect(model().attributeExists("projects"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("role"));
    }

    @Test
    void showProjects_shouldRedirectTologin_whenNotLoggedIn()  throws Exception {
        mockMvc.perform(get("/projects"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void showCreateProjectForm_shouldReturnView_whenProjectLeader() throws Exception {
        when(userService.getAllDevelopers()).thenReturn(List.of());

        mockMvc.perform(get("/projects/create")
                        .sessionAttr("userId", 1)
                        .sessionAttr("role", Role.PROJECT_LEADER))
                .andExpect(status().isOk())
                .andExpect(view().name("project-create"))
                .andExpect(model().attributeExists("new_project"))
                .andExpect(model().attributeExists("employees"));
    }

    @Test
    void showCreateProjectForm_shouldRedirect_whenNotLeader() throws Exception {
        mockMvc.perform(get("/projects/create")
                        .sessionAttr("userId", 1)
                        .sessionAttr("role", Role.DEVELOPER))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"));
    }

    @Test
    void viewProjectDetails_shouldLoadEverything() throws Exception {
        Project project = new Project();
        project.setProjectId(10);

        SubProject sp = new SubProject();
        sp.setSubProjectId(20);

        Task t = new Task();
        t.setTaskId(30);

        when(projectService.getByProjectId(10)).thenReturn(project);
        when(subProjectService.getAllSubProjectsByProjectId(10)).thenReturn(List.of(sp));
        when(taskService.getAllTaskBySubProjectId(20)).thenReturn(List.of(t));
        when(subTaskService.getAllSubTasksByTaskID(30)).thenReturn(List.of());

        mockMvc.perform(get("/projects/10/details")
                        .sessionAttr("userId", 1)
                        .sessionAttr("role", Role.DEVELOPER))
                .andExpect(status().isOk())
                .andExpect(view().name("project-details"))
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attributeExists("subProjects"))
                .andExpect(model().attributeExists("new_subproject"))
                .andExpect(model().attributeExists("new_task"))
                .andExpect(model().attributeExists("new_subtask"));
    }

}

