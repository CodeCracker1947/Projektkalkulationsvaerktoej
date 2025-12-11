package org.example.eksamenprojekt;

import org.example.eksamenprojekt.Model.User;
import org.example.eksamenprojekt.Model.Role;
import org.example.eksamenprojekt.Repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private UserRepo userRepo;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        userRepo = new UserRepo(jdbcTemplate);
    }

    @Test
    void findByUsername_shouldReturnUser_whenFound() {
        User madeUp = new User(1, "Issatou", "Kea123@gmail.dk", "123", Role.DEVELOPER);

        when(jdbcTemplate.query(
                eq("select * from Employee where Name = ?"),
                any(RowMapper.class),
                eq("Issatou")
        )).thenReturn(List.of(madeUp));

        User result = userRepo.findByUsername("Issatou");

        assertNotNull(result);
        assertEquals("Issatou", result.getName());
    }

    @Test
    void findByUsername_shouldReturnNull_whenNotfound() {
        when(jdbcTemplate.query(
                eq("select * from Employee where Name = ?"),
                any(RowMapper.class),
                eq("unknown")
        )).thenReturn(List.of());

        User result = userRepo.findByUsername("Unknown");

        assertNull(result);
    }

    @Test
    void findByUserId_shouldReturnUser() {
        User fake = new User(10, "John", "john@mail.dk", "pw", Role.DEVELOPER);

        when(jdbcTemplate.query(
                eq("select * from Employee where Id=?"),
                any(RowMapper.class),
                eq(10)
        )).thenReturn(List.of(fake));

        User result = userRepo.findByUserId(10);

        assertNotNull(result);
        assertEquals(10, result.getUserId());
    }

    @Test
    void save_shouldInsertUser_andReturnInsertedUser() {
        User newUser = new User(0, "NewUser1", "newUser1@hotmail.dk", "444", Role.DEVELOPER);
        User savedUser = new User(5, "NewUser1", "newUser1@hotmail.dk", "444", Role.DEVELOPER);

        doNothing().when(jdbcTemplate).update(anyString(), any(), any(), any(), any());


        when(jdbcTemplate.queryForObject(
                anyString(),
                any(RowMapper.class),
                eq("newUser1@hotmail.dk")
        )).thenReturn(savedUser);

        User result = userRepo.save(newUser);

        assertNotNull(result);
        assertEquals(5, result.getUserId());
        assertEquals("New", result.getName());

        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), any());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), any(RowMapper.class), eq("newUser1@hotmail.dk"));
    }
}
