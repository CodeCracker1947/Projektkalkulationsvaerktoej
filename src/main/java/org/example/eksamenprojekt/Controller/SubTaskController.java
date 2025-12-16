package org.example.eksamenprojekt.Controller;

import jakarta.servlet.http.HttpSession;
import org.example.eksamenprojekt.Model.Role;
import org.example.eksamenprojekt.Model.Status;
import org.example.eksamenprojekt.Model.SubTask;
import org.example.eksamenprojekt.Model.User;
import org.example.eksamenprojekt.Service.SubProjectService;
import org.example.eksamenprojekt.Service.SubTaskService;
import org.example.eksamenprojekt.Service.TaskService;
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
    private final TaskService taskService;
    private final SubProjectService subProjectService;

    public SubTaskController(SubTaskService subTaskService, UserService userService, TaskService taskService, SubProjectService subProjectService){
        this.subTaskService = subTaskService;
        this.userService = userService;
        this.taskService = taskService;
        this.subProjectService = subProjectService;
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
       if (role !=Role.PROJECT_LEADER) {
           return "redirect:/projects";
       }

       model.addAttribute("new_subtask", new SubTask());
        model.addAttribute("taskId", taskId);
        return "subtask-create";
    }

    @PostMapping("/create/{taskId}")
    public String createSubtask(@PathVariable int taskId, @ModelAttribute SubTask subTask, HttpSession session) {
        Role role = (Role) session.getAttribute("role");
        if (role != Role.PROJECT_LEADER) {
            return "redirect:/projects";
        }

        subTask.setTaskId(taskId);

        subTaskService.addSubTask(subTask);

        int subProjectId = taskService.getByTaskId(subTask.getTaskId()).getSubProjectId();
        int projectId = subProjectService.getBySubProjectId(subProjectId).getProjectId();

        return "redirect:/projects/" + projectId + "/details";
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
       // Integer userId = (Integer) session.getAttribute("userId");
       // if (userId == null) return "redirect:/login";

        subTaskService.updateSubTask(subtaskId, subTask);

        int subProjectId = taskService.getByTaskId(subTask.getTaskId()).getSubProjectId();
        int projectId = subProjectService.getBySubProjectId(subProjectId).getProjectId();

        return "redirect:/projects/" + projectId + "/details";
    }

    @PostMapping("/delete/{subtaskId}")
    public String deleteSubTask(@PathVariable int subtaskId, HttpSession session){
       /* Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        */

        SubTask subTask = subTaskService.getBySubTaskId(subtaskId);
        int taskId = subTask.getTaskId();

        subTaskService.delete(subtaskId);

        int subProjectId = taskService.getByTaskId(subTask.getTaskId()).getSubProjectId();
        int projectId = subProjectService.getBySubProjectId(subProjectId).getProjectId();

        return "redirect:/projects/" + projectId + "/details";
    }

    @PostMapping("/update-status/{subtaskId}")
    public String updateSubtaskStatus(@PathVariable int subtaskId,
                                      @RequestParam Status status,
                                      HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        SubTask subTask = subTaskService.getBySubTaskId(subtaskId);
        if (subTask == null) {
            return "redirect:/project-details";
        }

        subTask.setStatus(status);
        subTaskService.updateSubTask(subtaskId, subTask);

        int taskId = subTask.getTaskId();
        int subProjectId = taskService.getByTaskId(subTask.getTaskId()).getSubProjectId();
        int projectId = subProjectService.getBySubProjectId(subProjectId).getProjectId();

        return "redirect:/projects/" + projectId + "/details";
    }



}
