package org.example.eksamenprojekt.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenprojekt.Model.Project;
import org.example.eksamenprojekt.Model.User;
import org.example.eksamenprojekt.Service.ProjectService;
import org.example.eksamenprojekt.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class ProjectController {
    private final ProjectService service;
    private final UserService userService;

    public ProjectController (ProjectService service, UserService userService){
        this.service = service;
        this.userService = userService;
    }

    @GetMapping("/projects")
    public String showProjects(HttpSession session, Model model){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userService.getByUserId(userId);

        model.addAttribute("username", user.getName());
        model.addAttribute("projects", service.getAllProjectsByUserId(userId));
        model.addAttribute("new_project", new Project());

        return "projects";
    }

    @PostMapping("/add")
    public String addProject(HttpSession session, @ModelAttribute Project project){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        project.setUserId(userId);
        service.addProject(project);
        return "redirect:/project";
    }

    @GetMapping("/project/{projectId}/update")
    public String updateProject(@PathVariable int projectId, Model model){
        model.addAttribute("project", service.getByProjectId(projectId));
        return "update";
    }

    @PostMapping("/update")
    public String saveUpdate(@ModelAttribute Project model,HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Project existingProject = service.getByProjectId(model.getProjectId());
        if (Objects.equals(existingProject.getUserId(), userId)) {
            service.updateProject(model.getProjectId(), model);
        }

        return "redirect:/projects";
    }

    @PostMapping("/delete/{projectId}")
    public String deleteProject(@PathVariable int projectId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        Project project = service.getByProjectId(projectId);
        if (Objects.equals(project.getUserId(), userId)) {
            service.delete(projectId);
        }
        return "redirect:/projects";
    }
}
