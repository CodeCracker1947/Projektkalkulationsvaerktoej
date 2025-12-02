package org.example.eksamenprojekt.Controller;


import jakarta.servlet.http.HttpSession;
import org.example.eksamenprojekt.Model.SubProject;
import org.example.eksamenprojekt.Model.User;
import org.example.eksamenprojekt.Service.SubProjectService;
import org.example.eksamenprojekt.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class SubProjectController {
    private final UserService userService;
    private final SubProjectService subProjectService;

    public SubProjectController(UserService userService, SubProjectService subProjectService) {
       this.userService = userService;
        this.subProjectService = subProjectService;
    }

    @GetMapping("/subprojects")
    public String showSubProjects(HttpSession session, Model model){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userService.getByUserId(userId);

        model.addAttribute("username", user.getName());
        model.addAttribute("subprojects", subProjectService.getAllSubProjectsByUserId(userId));
        model.addAttribute("new_subproject", new SubProject());

        return "subprojects";
    }

    @GetMapping("/projects/{projectId}/subprojects/create")
    public String showCreateSubProjectForm(@PathVariable int projectId, Model model) {
        SubProject subProject = new SubProject();
        subProject.setProjectId(projectId);
        model.addAttribute("new_subproject", subProject);
        return "subproject-create";
    }

    @PostMapping("/projects/{projectId}/subprojects/create")
    public String createSubProject(@ModelAttribute SubProject subProject) {
        subProjectService.addSubProject(subProject);
        return "redirect:/projects/" + subProject.getProjectId();
    }

    @GetMapping("/subprojects/{subprojectId}")
    public String viewSubproject(@PathVariable int subprojectId, Model model){
        model.addAttribute("subproject", subProjectService.getBySubProjectId(subprojectId));
        return "subproject-details";
    }

    @GetMapping("/project/{projectId}/subprojects/update")
    public String updateSubProject(@PathVariable int subProjectId, Model model){
        model.addAttribute("project", subProjectService.getBySubProjectId(subProjectId));
        return "update";
    }

    @PostMapping("/update")
    public String saveUpdate(@ModelAttribute SubProject model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        SubProject existingSubProject = subProjectService.getBySubProjectId(model.getSubProjectId());
        if (Objects.equals(existingSubProject.getUserId(), userId)); {
            subProjectService.updateSubProject(model.getSubProjectId(), model);
        }
        return "redirect:/subprojects";
    }


    @PostMapping("/delete/{subprojectId")
    public String deleteSubProject(@PathVariable int subProjectId, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");

        SubProject subProject = subProjectService.getBySubProjectId(subProjectId);
        if (Objects.equals(subProject.getUserId(), userId)) {
            subProjectService.delete(subProjectId);
        }

        return "redirect:/subprojects";
    }
}