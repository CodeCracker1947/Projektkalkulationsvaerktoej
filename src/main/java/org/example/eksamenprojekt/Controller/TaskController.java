package org.example.eksamenprojekt.Controller;

import jakarta.servlet.http.HttpSession;
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
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userService.getByUserId(userId);

        model.addAttribute("username", user.getName());
        model.addAttribute("tasks", taskService.getAllTaskByUserId(userId));
        model.addAttribute("new_tasks", new Task());

        return "tasks";
    }

    @GetMapping("/subprojects/{subprojectId}/tasks/create")
    public String showCreateTaskForm(@PathVariable int subprojectId, Model model){
        Task task = new Task();
        task.setSubProjectId(subprojectId);
        model.addAttribute("new_task", task);
        return "task-create";

    }

    @PostMapping("/subprojects/{subprojectId}/tasks/create")
    public String createTask(@ModelAttribute Task task) {
        taskService.addTask(task);
        return "redirect:/subprojects/" + task.getSubProjectId();
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

    @PostMapping("/update")
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