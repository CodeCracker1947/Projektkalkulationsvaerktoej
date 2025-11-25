package org.example.eksamenprojekt.Repository;

import org.example.eksamenprojekt.Model.User;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserRepo {
    private final JdbcTemplate jdbcTemplate;
    public UserRepo(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByUsername(String name){
        return
    }

    public User findByEmail(String email){

    }

    public boolean avaliableUserName(String name){

    }

    public boolean avaliableEmail(String email){

    }

    public User findById(int id){

    }

    public User save(User user){

    }
}
