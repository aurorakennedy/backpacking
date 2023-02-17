package group61.backpacking;

import java.util.List;

import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestingWhileCoding {
    @Autowired
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    
    
    public TestingWhileCoding() {
        
    }

    

    public static Connection connectToDB() {
        Connection conn = null;
        String url = "jdbc:sqlite:sqlitesample.db";
        //"BACKPACKING\sqlitesample.db"
        //String url = "jdbc:sqlite:/../../../sqlitesample.db";

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
                System.out.println(resultSet.getString("password"));
                System.out.println(resultSet.getString("username"));
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


    public User loadUser1(String email) throws RuntimeException {
        
        try (Connection conn = connectToDB()){

            String sqlQuery = "SELECT * FROM User WHERE email = ?";
            RowMapper<User> rowMapper = new UserRowMapper();
            
            return jdbcTemplate.queryForObject(sqlQuery, rowMapper, email);
    
        } catch (SQLException e) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
    }

    public User loadUser(String email) throws RuntimeException, SQLException {
        try (Connection conn = connectToDB()) {
            String sqlQuery = "SELECT * FROM User WHERE email = ?";
            RowMapper<User> rowMapper = new UserRowMapper();
            return jdbcTemplate.queryForObject(sqlQuery, rowMapper, email);
            // return db.queryForObject(sqlQuery, rowMapper, email);
        } catch (SQLException e) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
    }

    public String userToString(User user) {
        return "User [email=" + user.getEmail() + ", password=" + user.getPassword() + ", username=" + user.getUserName() + "]";
    }

    public User loadUser2(String email) throws UserNotFoundException {
        String sql = "SELECT * FROM User WHERE email = ?";
        Object[] args = { email };
        RowMapper<User> rowMapper = new UserRowMapper();
        try {
            return jdbcTemplate.queryForObject(sql, args, rowMapper);
        } catch (EmptyResultDataAccessException ex) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
    }





    public static void main(String[] args) throws SQLException, RuntimeException {
        TestingWhileCoding t = new TestingWhileCoding();
        t.doStuff();
        //User user = new User("toob@test.com", "test", "tobbtest1");
        User user = t.loadUser("toob@test.com");
        System.out.println(t.userToString(user));

    }
    
}
