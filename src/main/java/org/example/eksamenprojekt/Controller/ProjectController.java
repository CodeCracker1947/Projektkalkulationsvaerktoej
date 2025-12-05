package org.example.eksamenprojekt.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenprojekt.Model.Project;
import org.example.eksamenprojekt.Model.Role;
import org.example.eksamenprojekt.Model.User;
import org.example.eksamenprojekt.Service.ProjectService;
import org.example.eksamenprojekt.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
@RequestMapping("projects")
@Controller
public class ProjectController {
    private final UserService userService;
    private final ProjectService projectService;

    public ProjectController ( UserService userService, ProjectService projectService){
        this.userService = userService;
        this.projectService = projectService;
    }

    @GetMapping()
    public String showProjects(HttpSession session, Model model){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        User user = userService.getByUserId(userId);
        model.addAttribute("username", user.getName());
        Role role = (Role) session.getAttribute("role");

        List<Project> projects = projectService.getAllProjectsByUserId(userId);;

        model.addAttribute("projects", projects);
        model.addAttribute("role", role);
        return "projects";
    }

    @GetMapping("/create")
    public String showCreateProjectForm(HttpSession session, Model model) {
      Integer userId = (Integer) session.getAttribute("userId");
      if (userId == null)
          return "redirect:/login";

        String role = (String) session.getAttribute("role");
         if (!"PROJECT_LEADER".equals(role)){
             return "redirect:/projects";
         }
         model.addAttribute("new_project", new Project());
         model.addAttribute("employees", userService.getAllDevelopers());
        return "project-create";
    }

    @GetMapping("/{id}/assign")
    public String showAssignForm(@PathVariable int projectId, Model model, HttpSession session) {
        Role role = (Role) session.getAttribute("Role");
        if (!"PROJECT_LEADER".equals(role)) {
            return "redirect:/projects";
        }
        model.addAttribute("projectId", projectId);
        model.addAttribute("employees", userService.getAllDevelopers());

        return "project-create";
    }

    @PostMapping("/{projectId}/assign")
    public String assignEmployee(@PathVariable int projectId, @RequestParam int userId){
        projectService.assignEmployeeToProject(projectId, userId);
        return "redirect:/projects";
    }


    @PostMapping("/create/{projectId}")
    public String createProject(@ModelAttribute Project project, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (!"PROJECT_LEADER".equals(role)){
            return "redirect:/projects";
        }

       projectService.addProject(project);
        return "redirect:/projects/";
    }

    @GetMapping("/{projectId}/update")
    public String updateProject(@PathVariable int projectId, Model model){
        model.addAttribute("project", projectService.getByProjectId(projectId));
        return "update";
    }

    @PostMapping("/{projectId}/update")
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
