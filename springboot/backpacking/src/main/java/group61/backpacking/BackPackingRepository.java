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
        String url = "jdbc:sqlite:database.db";
        

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
    public void createTable() throws SQLException{
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            
            conn = connectToDB();
            String sqlQuery = "CREATE TABLE User (email VARCHAR(50) PRIMARY KEY, password VARCHAR(20) NOT NULL, username VARCHAR(20) NOT NULL);";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            
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
               // do nothing
            }

    }

    // endre input til User user for at det skal fungere som det skal etter at vi har funnet ut hva som er problemet
    public User saveUser(User user1) throws SQLException, RuntimeException {
        
        try {
            System.out.println("repository:   "+user1.toString()); // username er null av en eller annen grunn
        } catch (Exception e) {
            throw new RuntimeException("user kan ikke skrives ut");
        }

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        // slett denne linjen når vi har funnet ut hva som er problemet
        User user = new User("drhdhrhf1@test.com", "rdgdff", "wggrddgd");

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
            // do nothing
            }
            
        try {
            //return loadUser(user.getEmail());
            return new User(user.getUserName(), user.getPassword(), user.getEmail());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        
    }
    public User loadUser(String email) throws RuntimeException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = new User(null,null,null);

        try  {
            conn = connectToDB();
            String sqlQuery = "SELECT * FROM User WHERE email = ?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            
            while (resultSet.next()) {
                user.mapUserFromResultSet(resultSet);
            }
              
        } catch (SQLException e) {
            throw new SQLException(e);
            // throw new UserNotFoundException("User with email " + email + " not found");
        }
        try {
            conn.close();
            statement.close();
            resultSet.close();
        } catch (Exception e) {
            // do nothing
        }
        return user;
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

    public User updateUser(User user, String password, String userName) throws RuntimeException, SQLException {

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

}
