package org.example.eksamenprojekt.Repository;

import org.example.eksamenprojekt.Model.Role;
import org.example.eksamenprojekt.Model.Status;
import org.example.eksamenprojekt.Model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class UserRepo {
    private final JdbcTemplate jdbcTemplate;

    public UserRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<User> userRowMapper = (rs, rowNum) ->
            new User(
                    rs.getInt("Employee_Id"),
                    rs.getString("Name"),
                    rs.getString("Email"),
                    rs.getString("Password"),
                    Role.valueOf(rs.getString("Role").toUpperCase())
            );


    public User findByUsername(String name) {
        String sql = "select * from Employee where Name = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, name);
        return users.isEmpty() ? null : users.get(0);

    }

    public User findByEmail(String email){
        String sql = "select * from Employee where Email =?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, email);
        return users.isEmpty() ? null : users.get(0);

    }

    public boolean avaliableUserName(String name){
        String sql = "select count(*) from Employee where Name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, name);
        return count != null && count > 0;

    }

    public boolean avaliableEmail(String email){
        String sql = "select count(*) from Employee where Email =?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;

    }

    public User findByUserId(int userId){
        String sql = "select * from Employee where Employee_Id";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, userId);
        return users.isEmpty() ? null : users.get(0);

    }

    public User save(User user){
        String sql = "insert into Employee (Employee_Id, Name, Email, Password, Role) values (?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"Employee_Id"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            //ps.setString(4, user.getRole());
            return ps;
        }, keyHolder);

        int generateID = keyHolder.getKey().intValue();
        user.setId(generateID);

        return user;
    }
}
