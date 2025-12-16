package org.example.eksamenprojekt.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenprojekt.Model.*;
import org.example.eksamenprojekt.Service.SubProjectService;
import org.example.eksamenprojekt.Service.TaskService;
import org.example.eksamenprojekt.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
@RequestMapping("tasks")
@Controller
public class TaskController {
    private final UserService userService;
    private final TaskService taskService;
    private final SubProjectService subProjectService;

    public TaskController(UserService userService, TaskService taskService, SubProjectService subProjectService){
        this.userService = userService;
        this.taskService = taskService;
        this.subProjectService = subProjectService;
    }

    @GetMapping()
    public String showTasks(HttpSession session, Model model) {
        int userId = (Integer) session.getAttribute("userId");

        List<Task> tasks = taskService.getAllTaskByUserId(userId);
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/subproject/{subprojectId}")
    public String viewTasksBySubProject(@PathVariable int subprojectId, Model model, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        List<Task> tasks = taskService.getAllTaskBySubProjectId(subprojectId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("subprojectId", subprojectId);
        return "tasks";
    }


    @GetMapping("/create/{subprojectId}")
    public String showCreateTaskForm(@PathVariable int subprojectId, HttpSession session, Model model){
        Role role = (Role) session.getAttribute("role");
        if (role != Role.PROJECT_LEADER) {
            return "redirect:/projects";
        }
        model.addAttribute("new_task", new Task());
        model.addAttribute("subprojectId", subprojectId);
        return "task-create";

    }

    @PostMapping("/create/{subprojectId}")
    public String createTask(@PathVariable int subprojectId, @ModelAttribute Task task, HttpSession session) {
        Role role = (Role) session.getAttribute("role");
        if (role !=Role.PROJECT_LEADER) {
            return "redirect:/projects";
        }
        task.setSubProjectId(subprojectId);
        Integer userId = (Integer) session.getAttribute("userId");
        task.setUserId(userId);

        taskService.addTask(task);

        SubProject subProject = subProjectService.getBySubProjectId(subprojectId);
        int projectId = subProject.getProjectId();
        return "redirect:/projects/" + projectId + "/details";
    }

    @GetMapping("/tasks{taskId}")
    public String viewTask(@PathVariable int taskId, Model model){
        model.addAttribute("task", taskService.getByTaskId(taskId));
        return "task-details";
    }

    @GetMapping("/update/{taskId}")
    public String updateTask(@PathVariable int taskId, Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        model.addAttribute("task", taskService.getByTaskId(taskId));
        return "task-update";
    }

    @PostMapping("/update/{taskId}")
    public String saveUpdate(@PathVariable int taskId, @ModelAttribute Task task, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Task existingTask = taskService.getByTaskId(task.getTaskId());
        if (Objects.equals(existingTask.getUserId(), userId)) {
            taskService.updateTask(taskId,task);
        }
        return "redirect:/tasks/subproject/" + task.getSubProjectId();
    }

    @PostMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable int taskId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        Task task = taskService.getByTaskId(taskId);
        int subprojectId = task.getSubProjectId();

        if (Objects.equals(task.getUserId(), userId)) {
            taskService.delete(taskId);
        }
        return "redirect:/tasks/subproject/" + subprojectId;
    }

    @PostMapping("/update-status/{taskId}")
    public String updateTaskStatus(@PathVariable int taskId,
                                   @RequestParam Status status,
                                   HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Task task = taskService.getByTaskId(taskId);
        if (task == null) {
            return "redirect:/project-details";
        }

        /*if (!Objects.equals(task.getUserId(), userId)) {
            return "redirect:/project-details";
        }

         */

        task.setStatus(status);
        taskService.updateTask(taskId, task);
        int subProjectId = taskService.getByTaskId(task.getTaskId()).getSubProjectId();
        int projectId = subProjectService.getBySubProjectId(subProjectId).getProjectId();

        return "redirect:/projects/" + projectId + "/details";
    }


}