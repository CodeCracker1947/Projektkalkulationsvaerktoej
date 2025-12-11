package org.example.eksamenprojekt;

import org.example.eksamenprojekt.Model.Role;
import org.example.eksamenprojekt.Model.User;
import org.example.eksamenprojekt.Repository.UserRepo;
import org.example.eksamenprojekt.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepo userRepo;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepo = mock(UserRepo.class);
        userService = new UserService(userRepo);
    }

    @Test
    void registerUser_success() {
        User user = new User( "TestUser3", "TestUser3@outlook.com", "111", Role.DEVELOPER);

        when(userRepo.avaliableUserName("TestUser3")).thenReturn(false);
        when(userRepo.avaliableEmail("TestUser3@outlook.com")).thenReturn(false);
        when(userRepo.save(any(User.class))).thenReturn(user);

        User result = userService.registerUser(user);

        assertNotNull(result);
        assertEquals("TestUser3", result.getName());
        verify(userRepo).save(any(User.class));
    }

    @Test
    void registerUser_emailAlreadyExists_Exception() {
        User user = new User("TestUser4", "TestUser4@yahoo.com", "333", Role.PROJECT_LEADER);

        when(userRepo.avaliableEmail("TestUser4@yahoo.com")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,() -> userService.registerUser(user));
        verify(userRepo, never()).save(any());
    }

}
