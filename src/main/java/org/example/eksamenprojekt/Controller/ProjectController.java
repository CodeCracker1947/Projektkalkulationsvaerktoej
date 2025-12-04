package org.example.eksamenprojekt.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenprojekt.Model.Project;
import org.example.eksamenprojekt.Model.Role;
import org.example.eksamenprojekt.Model.User;
import org.example.eksamenprojekt.Service.ProjectService;
import org.example.eksamenprojekt.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class ProjectController {
    private final UserService userService;
    private final ProjectService projectService;

    public ProjectController ( UserService userService, ProjectService projectService){
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public String showProjects(HttpSession session, Model model){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        Role role = (Role) session.getAttribute("role");

        List<Project> projects = projectService.getAllProjectsByUserId(userId);;

        model.addAttribute("projects", projects);
        model.addAttribute("role", role);
        return "projects";
    }

    @GetMapping("/projects/create")
    public String showCreateProjectForm(HttpSession session, Model model) {
      Integer userId = (Integer) session.getAttribute("userId");
      if (userId == null)
          return "redirect:/login";

        String role = (String) session.getAttribute("role");
         if (!"PROJECT_LEADER".equals(role)){
             return "redirect:/projects";
         }
         model.addAttribute("new_project", new Project());
        return "project-create";
    }

    @PostMapping("project/create")
    public String createProject(@ModelAttribute Project project, HttpSession session) {
        String role = (String) session.getAttribute("role");

        if (!"PROJECT_LEADER".equals(role)){
            return "redirect:/projects";
        }

        projectService.addProject(project);
        return "project-create";
    }

    @GetMapping("/project/{projectId}/update")
    public String updateProject(@PathVariable int projectId, Model model){
        model.addAttribute("project", projectService.getByProjectId(projectId));
        return "update";
    }

    @PostMapping("/project/{projectId}/update")
    public String saveUpdate(@ModelAttribute Project model,HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Project existingProject = projectService.getByProjectId(model.getProjectId());
        if (Objects.equals(existingProject.getUserId(), userId)) {
            projectService.updateProject(model.getProjectId(), model);
        }

        return "redirect:/projects";
    }

    @PostMapping("/delete/{projectId}")
    public String deleteProject(@PathVariable int projectId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        Project project = projectService.getByProjectId(projectId);
        if (Objects.equals(project.getUserId(), userId)) {
            projectService.delete(projectId);
        }
        return "redirect:/projects";
    }
}
