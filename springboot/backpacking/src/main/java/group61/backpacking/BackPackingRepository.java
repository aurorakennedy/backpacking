package group61.backpacking;

import java.util.List;

import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Repository
public class BackPackingRepository {

    @Autowired
    private JdbcTemplate db;

    // public BackPackingRepository(JdbcTemplate jdbcTemplate) {
    //     this.jdbcTemplate = jdbcTemplate;
    // }

    

    public static Connection connectToDB() {
        Connection conn = null;
        String url = "jdbc:sqlite:sqlitesample.db";
        

        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite database established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public void doStuff() throws RuntimeException{
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            conn = connectToDB();
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM User");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("email"));
        }
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            resultSet.close();
            statement.close();
            conn.close();
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        

        
    }


    public User saveUser(User user) throws SQLException, RuntimeException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            
            conn = connectToDB();
            String sqlQuery = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            //db.update(preparedStatement, user.getUserName(), user.getPassword(), user.getEmail());
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
            //throw new DuplicateUserException("User with email " + user.getEmail() + " already exists");   
        }

        try {
            resultSet.close();
            statement.close();
            conn.close();
                
        } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
            
        try {
            return loadUser(user.getEmail());
        } catch (RuntimeException e) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " not found");
        
        }

        
    }

    public User loadUser(String email) throws RuntimeException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {

            String sqlQuery = "SELECT * FROM User WHERE email = ?";
            RowMapper<User> rowMapper = new UserRowMapper();
            
            return db.queryForObject(sqlQuery, rowMapper, email);
    
        } catch (RuntimeException e) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
    }

    public void deleteUser(User user) throws RuntimeException{
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sqlQuery = "DELETE FROM User (username, password, email) VALUES (?, ?, ?)";
            db.update(sqlQuery, user.getUserName(), user.getPassword(), user.getEmail());
        } catch (RuntimeException e) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " not found");
        }
    }

// yoyo
    public User login(User user) throws RuntimeException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String sqlQuery = "SELECT * FROM User WHERE email = ? AND password = ?";
            RowMapper<User> rowMapper = new UserRowMapper();
            User loginUser = db.queryForObject(sqlQuery, rowMapper, user.getEmail(), user.getPassword());
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
        
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            if (login(user) == null) return false;

            String sqlQuery = "SELECT email, password, username" +
            "FROM User"+
            "INNER RIGHT JOIN Moderator" +
            "ON User.email = Moderator.email" +
            "WHERE Moderator.email = ?;";

            RowMapper<User> rowMapper = new UserRowMapper();
            User loginUser = db.queryForObject(sqlQuery, rowMapper, user.getEmail());
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

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            String sqlQuery = "UPDATE User SET username = ?, password = ? WHERE email = ?";
            db.update(sqlQuery, userName, password,  user.getEmail());
        } catch (RuntimeException e) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " not found");
        }
        try {
            return loadUser(user.getEmail());
        } catch (RuntimeException e) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " not found");
        }
    }

    public void createUser(String email, String password, String username){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setUserName(username);

        saveUser(user);
    }
}
