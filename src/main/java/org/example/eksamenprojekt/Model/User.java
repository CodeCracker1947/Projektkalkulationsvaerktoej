package org.example.eksamenprojekt.Model;

public class User {
    private int userId;
    private String name;
    private Role role;
    private String email;
    private String password;

    private transient String confirmPassword;

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

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
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
    //når vi laver ny bruger, skal springboot kunne læse enums som en string og give role.delevoper (feks.)
    public void setRole(String role) {
        if (role != null) {
            this.role = Role.valueOf(role.toUpperCase());
        }
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
