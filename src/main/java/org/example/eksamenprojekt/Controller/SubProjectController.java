package org.example.eksamenprojekt.Controller;


import jakarta.servlet.http.HttpSession;
import org.example.eksamenprojekt.Model.Role;
import org.example.eksamenprojekt.Model.SubProject;
import org.example.eksamenprojekt.Service.SubProjectService;
import org.example.eksamenprojekt.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequestMapping("subprojects")
@Controller
public class SubProjectController {
    private final UserService userService;
    private final SubProjectService subProjectService;

    public SubProjectController(UserService userService, SubProjectService subProjectService) {
       this.userService = userService;
        this.subProjectService = subProjectService;
    }

    @GetMapping()
    public String showSubProjects(HttpSession session, Model model){
        int userId = (Integer) session.getAttribute("userId");

        List<SubProject> subProjects = subProjectService.getAllSubProjectsByUserId(userId);
        model.addAttribute("subprojects", subProjects);
        return "subprojects";
    }

    @GetMapping("/projects/{projectId}")
    public String viewSubProjectByProject(@PathVariable int projectId, Model model, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        List<SubProject> subProjects = subProjectService.getAllSubProjectsByProjectId(projectId);
        model.addAttribute("subProjects", subProjects);
        model.addAttribute("projectId", projectId);
        return "project-details";
    }

    @GetMapping("/create/{projectId}")
    public String showCreateSubProjectForm(@PathVariable int projectId, HttpSession session, Model model) {
        Role role = (Role) session.getAttribute("role");
        if (role != Role.PROJECT_LEADER){
            return "redirect:/projects";
        }
        model.addAttribute("new_subproject", new SubProject());
        model.addAttribute("projectId", projectId);
        return "subproject-create";
    }

    @PostMapping("/create/{projectId}")
    public String createSubProject(@PathVariable int projectId, @ModelAttribute SubProject subProject, HttpSession session) {
        Role role = (Role) session.getAttribute("role");
        if (role != Role.PROJECT_LEADER)
            return "redirect:/projects";

        subProject.setProjectId(projectId);
        Integer userId = (Integer) session.getAttribute("userId");
        subProject.setUserId(userId);

        subProjectService.addSubProject(subProject);
        return "redirect:/subprojects/project/" + projectId;
    }


    @GetMapping("/project/{projectId}")
    public String viewSubProjectsByProject(@PathVariable int projectId, Model model, HttpSession session){
      Integer userId = (Integer) session.getAttribute("userId");
      if (userId == null) return "redirect:/login";

        List<SubProject> subProjects = subProjectService.getAllSubProjectsByProjectId(projectId);
        model.addAttribute("subProjects", subProjects);
        model.addAttribute("projectId", projectId);
        return "subprojects";
    }

    @GetMapping("/{subProjectId}")
    public String viewSubproject(@PathVariable int subprojectId, Model model){
        model.addAttribute("subproject", subProjectService.getBySubProjectId(subprojectId));
        return "subproject-details";
    }

    @GetMapping("/update/{subProjectId}")
    public String updateSubProject(@PathVariable int subProjectId, Model model, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        model.addAttribute("subproject", subProjectService.getBySubProjectId(subProjectId));
        return "subproject-update";
    }

    @PostMapping("/update/{subProjectId}")
    public String saveUpdate(@PathVariable int subProjectId, @ModelAttribute SubProject subProject, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        subProjectService.updateSubProject(subProjectId,subProject);
        return "redirect:/subprojects/project/" + subProject.getProjectId();
    }


    @PostMapping("/delete/{subProjectId}")
    public String deleteSubProject(@PathVariable int subProjectId, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        SubProject subProject = subProjectService.getBySubProjectId(subProjectId);
        int projectId = subProject.getProjectId();

        subProjectService.delete(subProjectId);
        return "redirect:/subprojects/project/" + projectId;
    }
}