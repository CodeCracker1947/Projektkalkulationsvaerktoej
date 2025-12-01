package org.example.eksamenprojekt.Model;

public class User {
    private int userId;
    private String name;
    private Role role;
    private String email;
    private String password;

    public User(int userId, String name, String email, String password, Role role){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;

    }

    public User(String name, String email, String password, Role role){
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(){

    }

    public int getId(){
        return userId;
    }

    public void setId(int userId){
        this.userId = userId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Role getRole(){
        return role;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
}
