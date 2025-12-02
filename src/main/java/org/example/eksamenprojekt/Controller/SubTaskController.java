package org.example.eksamenprojekt.Controller;

import jakarta.servlet.http.HttpSession;
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
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        User user = userService.getByUserId(userId);

        model.addAttribute("username", user.getName());
        model.addAttribute("subtasks", subTaskService.getAllSubTaskByUserId(userId));
        model.addAttribute("new_subtask", new SubTask());

        return "subtasks";
    }

    @GetMapping("/tasks/{taskId}/subtasks/create")
    public String showCreateSubtaskForm(@PathVariable int taskId, Model model){
        SubTask subTask = new SubTask();
        subTask.setTaskId(taskId);
        model.addAttribute("new_subtask", subTask);
        return "subtask-create";
    }

    @PostMapping("/tasks{taskId}/subtasks/create")
    public String createSubtask(@ModelAttribute SubTask subTask) {
        subTaskService.addSubTask(subTask);
        return "redirect:/tasks/" + subTask.getTaskId();
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

    @PostMapping("/delete/{subprojectId}")
    public String deleteSubTask(@PathVariable int subprojectId, HttpSession session){
        Integer userId = (Integer) session.getAttribute("userId");

        SubTask subTask = subTaskService.getBySubTaskId(subprojectId);
        if (Objects.equals(subTask.getUserId(),userId)){
            subTaskService.delete(subprojectId);
        }

        return "redirect:/subtasks";
    }
}
