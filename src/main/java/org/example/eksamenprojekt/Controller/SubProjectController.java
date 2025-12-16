package org.example.eksamenprojekt.Controller;


import jakarta.servlet.http.HttpSession;
import org.example.eksamenprojekt.Model.Role;
import org.example.eksamenprojekt.Model.SubProject;
import org.example.eksamenprojekt.Service.SubProjectService;
import org.example.eksamenprojekt.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("subprojects")
@Controller
public class SubProjectController {
    private final UserService userService;
    private final SubProjectService subProjectService;

    public SubProjectController(UserService userService, SubProjectService subProjectService) {
       this.userService = userService;
        this.subProjectService = subProjectService;
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
        return "redirect:/projects/" + projectId + "/details";
    }

    @PostMapping("/update/{subProjectId}")
    public String saveUpdate(@PathVariable int subProjectId, @ModelAttribute SubProject subProject, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        subProjectService.updateSubProject(subProjectId,subProject);
        return "redirect:/projects/" + subProject.getProjectId();
    }


    @PostMapping("/delete/{subProjectId}")
    public String deleteSubProject(@PathVariable int subProjectId, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        SubProject subProject = subProjectService.getBySubProjectId(subProjectId);
        int projectId = subProject.getProjectId();

        subProjectService.delete(subProjectId);
        return "redirect:/projects/" + projectId + "/details";
    }
}