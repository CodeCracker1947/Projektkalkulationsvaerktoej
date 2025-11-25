package org.example.eksamenprojekt.Service;

import org.example.eksamenprojekt.Model.User;
import org.example.eksamenprojekt.Repository.UserRepo;

public class UserService {
    private final UserRepo Repository;

    public UserService(UserRepo repository){
        this.Repository = repository;
    }

    public User registerUser(User user){

    }

    public boolean login(String name, String password){

    }

    public User getUsername(String name){

    }

    public User getEmail(String email){

    }

    public User getById(int id){

    }
}
