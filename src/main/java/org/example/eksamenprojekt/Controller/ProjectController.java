package org.example.eksamenprojekt.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenprojekt.Model.*;
import org.example.eksamenprojekt.Service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Objects;

@RequestMapping("projects")
@Controller
public class ProjectController {
    private final UserService userService;
    private final ProjectService projectService;
    private final SubProjectService subProjectService;
    private final TaskService taskService;
    private final SubTaskService subTaskService;


    public ProjectController ( UserService userService,
                               ProjectService projectService,
                               SubProjectService subProjectService,
                               TaskService taskService,
                               SubTaskService subTaskService){
        this.userService = userService;
        this.projectService = projectService;
        this.subProjectService = subProjectService;
        this.taskService = taskService;
        this.subTaskService = subTaskService;
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

        List<Project> projects = projectService.getAllProjectsByUserId(userId);

        model.addAttribute("projects", projects);
        model.addAttribute("role", role);
        return "projects";
    }

    @GetMapping("/create")
    public String showCreateProjectForm(HttpSession session, Model model) {
      Integer userId = (Integer) session.getAttribute("userId");
      if (userId == null)
          return "redirect:/login";

       Role role = (Role) session.getAttribute("role");
         if (role != Role.PROJECT_LEADER){
             return "redirect:/projects";
         }

         model.addAttribute("new_project", new Project());
         model.addAttribute("employees", userService.getAllDevelopers());
        return "project-create";
    }

    @GetMapping("/{projectId}/assign")
    public String showAssignForm(@PathVariable int projectId, Model model, HttpSession session) {
        Role role = (Role) session.getAttribute("role");
        if (role != Role.PROJECT_LEADER){
            return "redirect:/projects";
        }
        model.addAttribute("projectId", projectId);
        model.addAttribute("employees", userService.getAllDevelopers());

        return "project-assign";
    }

    @PostMapping("/{projectId}/assign")
    public String assignEmployee(@PathVariable int projectId, @RequestParam int userId){
        projectService.assignEmployeeToProject(projectId, userId);
        return "redirect:/projects";
    }

    @PostMapping("/create")
    public String createProject(@ModelAttribute Project project, @RequestParam(required = false) List<Integer> userIds, HttpSession session, RedirectAttributes redirectAttributes) {
        Role role = (Role) session.getAttribute("role");
        if (role != Role.PROJECT_LEADER){
            return "redirect:/projects";
        }

        Integer userId = (Integer) session.getAttribute("userId");
        project.setUserId(userId);

        projectService.addProject(project);
        int projectId = projectService.getLastInsertedProjectId();

        projectService.assignEmployeeToProject(projectId,userId);

        if (userIds != null && !userIds.isEmpty()) {
            for (Integer selectedUserId : userIds) {
               projectService.assignEmployeeToProject(projectId, selectedUserId);
            }
        }

        redirectAttributes.addFlashAttribute("success","Projektet '" + project.getName() + "' er nu oprettet");
        return "redirect:/projects";
    }

    @GetMapping("/{projectId}/update")
    public String updateProject(@PathVariable int projectId, Model model){
        model.addAttribute("project", projectService.getByProjectId(projectId));
        return "update";
    }

    @GetMapping("/{projectId}/details")
    public String viewProjectDetails(@PathVariable int projectId, Model model, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Role role = (Role) session.getAttribute("role");

        Project project = projectService.getByProjectId(projectId);

        List<SubProject> subProjects = subProjectService.getAllSubProjectsByProjectId(projectId);
        for (SubProject sp: subProjects){

            List<Task> tasks = taskService.getAllTaskBySubProjectId(sp.getSubProjectId());
            sp.setTasks(tasks);

            for (Task t: tasks){
                List<SubTask> subTasks = subTaskService.getAllSubTasksByTaskID(t.getTaskId());
                t.setSubtasks(subTasks);
            }
        }

        model.addAttribute("project", project);
        model.addAttribute("subProjects", subProjects);
        model.addAttribute("role", role);

        model.addAttribute("new_subproject", new SubProject());
        model.addAttribute("new_task", new Task());
        model.addAttribute("new_subtask", new SubTask());

        return "project-details";
    }

    @PostMapping("/{projectId}/update")
    public String saveUpdate(@PathVariable int projectId, @ModelAttribute Project project,HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Project existingProject = projectService.getByProjectId(projectId);
        if (Objects.equals(existingProject.getUserId(), userId)) {
            projectService.updateProject(projectId, project);
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
