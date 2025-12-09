package org.example.eksamenprojekt.Controller;


import jakarta.servlet.http.HttpSession;
import org.example.eksamenprojekt.Model.User;
import org.example.eksamenprojekt.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, HttpSession session, Model model) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("registerError", "Passwords do not match!");
            return "register";
        }
        try {
            User newUser = service.registerUser(user);
            session.setAttribute("userId", newUser.getUserId());
            return "redirect:/projects";
        } catch (IllegalArgumentException e) {
            model.addAttribute("registerError", e.getMessage());
            return "register";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session, Model model) {

        if (service.login(username, password)) {
            User user = service.getUsername(username);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("role", user.getRole());
            return "redirect:/projects";
        } else {
            model.addAttribute("loginError", "Brugernavn eller password er forkert");
            return "login";
        }
    }

    @GetMapping("/user/{username}")
    @ResponseBody
    public User getUser(@PathVariable String username) {
        return service.getUsername(username);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return service.getEmail(email);
    }

}
