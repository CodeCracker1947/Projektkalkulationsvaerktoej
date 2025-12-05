package org.example.eksamenprojekt.Service;

import org.example.eksamenprojekt.Model.User;
import org.example.eksamenprojekt.Repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepo repository;

    public UserService(UserRepo repository){
        this.repository = repository;
    }

    public User registerUser(User user) {
        if (repository.avaliableUserName(user.getName())) {
            throw new IllegalArgumentException("Brugernavnet findes allerede");
        }
        if (repository.avaliableEmail(user.getEmail())) {
            throw new IllegalArgumentException("Emailen er allerede i brug");
        }
         return repository.save(user);
    }

    public boolean login(String name, String password){
    User user = repository.findByUsername(name);
    if (user == null) return false;
    return user.getPassword().equals(password);
    }

    public User getUsername(String name){
    return repository.findByUsername(name);
    }

    public User getEmail(String email){
    return repository.findByEmail(email);
    }

    public User getByUserId(int userId){
    return repository.findByUserId(userId);
    }
    public List<User> getAllDevelopers() {
        return repository.findAllEmployees();
    }
}
