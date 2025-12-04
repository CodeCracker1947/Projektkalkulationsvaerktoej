package org.example.eksamenprojekt.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenprojekt.Model.Role;
import org.example.eksamenprojekt.Model.Task;
import org.example.eksamenprojekt.Model.User;
import org.example.eksamenprojekt.Service.TaskService;
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
public class TaskController {
    private final UserService userService;
    private final TaskService taskService;

    public TaskController(UserService userService, TaskService taskService){
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String showTasks(HttpSession session, Model model) {
        int userId = (Integer) session.getAttribute("userId");

        List<Task> tasks = taskService.getAllTaskByUserId(userId);
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @GetMapping("/subprojects/{subprojectId}/tasks/create")
    public String showCreateTaskForm(HttpSession session, Model model){
        Role role = (Role) session.getAttribute("Role");
        if (!"PROJECT_LEADER".equals(role)) {
            return "redirect:/tasks";
        }
        model.addAttribute("new_task", new Task());
        return "task-create";

    }

    @PostMapping("/subprojects/{subprojectId}/tasks/create")
    public String createTask(@ModelAttribute Task task, HttpSession session) {
        Role role = (Role) session.getAttribute("Role");
        if (!"PROJECT_LEADER".equals(role)) {
            return "redirect:/tasks";
        }
        taskService.addTask(task);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks{taskId}")
    public String viewTask(@PathVariable int taskId, Model model){
        model.addAttribute("task", taskService.getByTaskId(taskId));
        return "task-details";
    }

    @GetMapping("/task/{taskId}/update")
    public String updateTask(@PathVariable int taskId, Model model) {
        model.addAttribute("task", taskService.getByTaskId(taskId));
        return "update";
    }

    @PostMapping("/task/{taskId}/update")
    public String saveUpdate(@ModelAttribute Task model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Task existingTask = taskService.getByTaskId(model.getTaskId());
        if (Objects.equals(existingTask.getUserId(), userId)) {
            taskService.updateTask(model.getTaskId(), model);
        }
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable int taskId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        Task task = taskService.getByTaskId(taskId);
        if (Objects.equals(task.getUserId(), userId)) {
            taskService.delete(taskId);
        }
        return "redirect:/tasks";
    }
}