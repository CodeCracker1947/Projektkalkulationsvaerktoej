package org.example.eksamenprojekt;

import org.example.eksamenprojekt.Controller.UserController;
import org.example.eksamenprojekt.Model.Role;
import org.example.eksamenprojekt.Model.User;
import org.example.eksamenprojekt.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void login_Success_redirectToProject() throws Exception {
        User user = new User(1, "TestUser1", "TestUser@gmail.com", "1234", Role.DEVELOPER);

        when(userService.login("TestUser1", "1234")).thenReturn(true);
        when(userService.getUsername("TestUser1")).thenReturn(user);

        mockMvc.perform(post("/login").param("username", "TestUser1")
                .param("password", "1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/projects"))
                .andExpect(request().sessionAttribute("userId", 1))
                .andExpect(request().sessionAttribute("role", Role.DEVELOPER));
    }
    @Test
    void login_failure_returnsLoginView() throws Exception {
        when(userService.login("TestUser1", "wrong")).thenReturn(false);

        mockMvc.perform(post("/login")
                .param("username", "TestUser1")
                .param("password","wrong"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("loginError"));
    }
    @Test
    void getUser_returnsUser() throws Exception {
        User user = new User(2, "TestUser2", "TestUser@hotmail.com", "22", Role.PROJECT_LEADER);

        when(userService.getUsername("TestUser2")).thenReturn(user);

        mockMvc.perform(get("/user/TestUser2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestUser2"))
                .andExpect(jsonPath("$.email").value("TestUser@hotmail.com"));
    }

    @Test
    void register_password_and_ConfirmPasswordDontMatch() throws Exception {
        mockMvc.perform(post("/register")
                .param("name", "newUser1")
                .param("email", "newUser@gmail.com")
                .param("password", "100")
                .param("confirmPassword", "200"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("registerError"));
    }
}
