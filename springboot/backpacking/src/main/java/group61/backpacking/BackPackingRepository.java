package group61.backpacking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class BackPackingRepository {

    @Autowired
    private JdbcTemplate db;

    // public BackPackingRepository(JdbcTemplate jdbcTemplate) {
    //     this.jdbcTemplate = jdbcTemplate;
    // }

    public User saveUser(User user) throws RuntimeException {
        try {
            String sql = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
            db.update(sql, user.getUserName(), user.getPassword(), user.getEmail());
        } catch (RuntimeException e) {
            throw new DuplicateUserException("User with email " + user.getEmail() + " already exists");
            
        }
        try {
            return loadUser(user.getEmail());
        } catch (RuntimeException e) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " not found");
        
        }
        
    }

    public User loadUser(String email) throws RuntimeException {
        try {

            String sql = "SELECT * FROM User WHERE email = ?";
            RowMapper<User> rowMapper = new UserRowMapper();
            
            return db.queryForObject(sql, rowMapper, email);
    
        } catch (RuntimeException e) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
    }

    public void deleteUser(User user) throws RuntimeException{
        try {
            String sql = "DELETE FROM User (username, password, email) VALUES (?, ?, ?)";
            db.update(sql, user.getUserName(), user.getPassword(), user.getEmail());
        } catch (RuntimeException e) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " not found");
        }
    }


    public User login(User user) throws RuntimeException {
        try {
            String sql = "SELECT * FROM User WHERE email = ? AND password = ?";
            RowMapper<User> rowMapper = new UserRowMapper();
            User loginUser = db.queryForObject(sql, rowMapper, user.getEmail(), user.getPassword());
            if (loginUser != null) {
                return loginUser;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " not found");
        }
    }

    public boolean isAdmin(User user) throws RuntimeException {
        try {
            
            if (login(user) == null) {
                return false;
            }
            String modEmailQuery = "SELECT * FROM Moderator WHERE email = ?";
            RowMapper<User> rowMapper = new UserRowMapper();
            User loginUser = db.queryForObject(modEmailQuery, rowMapper, user.getEmail(), user.getPassword());
            if (loginUser != null) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            throw new UserNotFoundException("Something went wrong");
        }
    }

    public User updateUser(User user, String password, String userName) throws RuntimeException {
        try {
            String sql = "UPDATE User SET username = ?, password = ? WHERE email = ?";
            db.update(sql, userName, password,  user.getEmail());
        } catch (RuntimeException e) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " not found");
        }
        try {
            return loadUser(user.getEmail());
        } catch (RuntimeException e) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " not found");
        }
    }


}
