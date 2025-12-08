package org.example.eksamenprojekt.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenprojekt.Model.Role;
import org.example.eksamenprojekt.Model.SubTask;
import org.example.eksamenprojekt.Model.User;
import org.example.eksamenprojekt.Service.SubTaskService;
import org.example.eksamenprojekt.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ListResourceBundle;
import java.util.Objects;

@RequestMapping("subtasks")
@Controller
public class SubTaskController {
    private final SubTaskService subTaskService;
    private final UserService userService;

    public SubTaskController(SubTaskService subTaskService, UserService userService){
        this.subTaskService = subTaskService;
        this.userService = userService;
    }

    @GetMapping("/task/{taskId}")
    public String viewSubtasksByTask(@PathVariable int taskId, Model model, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        List<SubTask> subTasks = subTaskService.getAllSubTasksByTaskID(taskId);
        model.addAttribute("subtasks", subTasks);
        model.addAttribute("taskId", taskId);
        return "subtasks";

    }


    @GetMapping()
    public String showSubtasks(HttpSession session, Model model){
        int userId = (Integer) session.getAttribute("userId");

        List<SubTask> subTasks = subTaskService.getAllSubTaskByUserId(userId);
        model.addAttribute("subtasks", subTasks);
        return "subtasks";
    }

    @GetMapping("/create/{taskId}")
    public String showCreateSubtaskForm(@PathVariable int taskId, HttpSession session, Model model){
       Role role = (Role) session.getAttribute("role");
       if (!"PROJECT_LEADER".equals(role)) {
           return "redirect:/projects";
       }

       model.addAttribute("new_subtask", new SubTask());
        model.addAttribute("taskId", taskId);
        return "subtask-create";
    }

    @PostMapping("/tasks{taskId}")
    public String createSubtask(@PathVariable int taskId, @ModelAttribute SubTask subTask, HttpSession session) {
        Role role = (Role) session.getAttribute("role");
        if (!"PROJECT_LEADER".equals(role)) {
            return "redirect:/projects";
        }

        subTask.setTaskId(taskId);
        Integer userId = (Integer) session.getAttribute("userId");
        subTask.setUserId(userId);

        subTaskService.addSubTask(subTask);

        return "redirect:/subtasks/task/" + taskId;
    }

    @GetMapping("/update/{subtaskId}")
    public String updateSubtask(@PathVariable int subtaskId, Model model, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        model.addAttribute("subtask",subTaskService.getBySubTaskId(subtaskId));
        return "subtask-update";
    }

    @PostMapping("/update/{subtaskId}")
    public String saveUpdate(@PathVariable int subtaskId, @ModelAttribute SubTask subTask, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

       SubTask existingSubTask = subTaskService.getBySubTaskId(subTask.getSubTaskId());
       if (Objects.equals(existingSubTask.getUserId(), userId)) {
           subTaskService.updateSubTask(subtaskId, subTask);
       }

       return "redirect:/subtasks/task/" + subTask.getTaskId();
    }

    @PostMapping("/delete/{subtaskId}")
    public String deleteSubTask(@PathVariable int subtaskId, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        SubTask subTask = subTaskService.getBySubTaskId(subtaskId);
        int taskId = subTask.getTaskId();

        if (Objects.equals(subTask.getUserId(),userId)){
            subTaskService.delete(subtaskId);
        }

        return "redirect:/subtasks/task" + taskId;
    }
}
