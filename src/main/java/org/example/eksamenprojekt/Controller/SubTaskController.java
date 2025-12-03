package org.example.eksamenprojekt.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenprojekt.Model.Role;
import org.example.eksamenprojekt.Model.SubTask;
import org.example.eksamenprojekt.Model.User;
import org.example.eksamenprojekt.Service.SubTaskService;
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
public class SubTaskController {
    private final SubTaskService subTaskService;
    private final UserService userService;

    public SubTaskController(SubTaskService subTaskService, UserService userService){
        this.subTaskService = subTaskService;
        this.userService = userService;
    }

    @GetMapping("subtasks")
    public String showSubtasks(HttpSession session, Model model){
        int userId = (Integer) session.getAttribute("userId");

        List<SubTask> subTasks = subTaskService.getAllSubTaskByUserId(userId);
        model.addAttribute("subtasks", subTasks);
        return "subtasks";
    }

    @GetMapping("/tasks/{taskId}/subtasks/create")
    public String showCreateSubtaskForm(HttpSession session, Model model){
       Role role = (Role) session.getAttribute("Role");
       if (!"PROJECT_LEADER".equals(role)) {
           return "redirect:/subtasks";
       }
        model.addAttribute("new_subtask", new SubTask());
        return "subtask-create";
    }

    @PostMapping("/tasks{taskId}/subtasks/create")
    public String createSubtask(@ModelAttribute SubTask subTask, HttpSession session) {
        Role role = (Role) session.getAttribute("Role");
        if (!"PROJECT_LEADER".equals(role)) {
            return "redirect:/subtasks";
        }
        return "redirect:/subtasks";
    }

    @GetMapping("/subtask/{subtaskId}/update")
    public String updateSubtask(@PathVariable int subtaskId, Model model){
        model.addAttribute("subtask",subTaskService.getBySubTaskId(subtaskId));
        return "update";
    }

    @PostMapping("/update")
    public String saveUpdate(@ModelAttribute SubTask model, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

       SubTask existingSubTask = subTaskService.getBySubTaskId(model.getSubTaskId());
       if (Objects.equals(existingSubTask.getUserId(), userId)) {
           subTaskService.updateSubTask(model.getSubTaskId(), model);
       }

       return "redirect:subtasks";
    }

    @PostMapping("/delete/{subtaskId}")
    public String deleteSubTask(@PathVariable int subprojectId, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");

        SubTask subTask = subTaskService.getBySubTaskId(subprojectId);
        if (Objects.equals(subTask.getUserId(),userId)){
            subTaskService.delete(subprojectId);
        }

        return "redirect:/subtasks";
    }
}
