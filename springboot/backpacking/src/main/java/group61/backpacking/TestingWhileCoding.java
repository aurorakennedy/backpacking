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

public class TestingWhileCoding {

    
    
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

    // public User mapUserFromResultSet(ResultSet resultSet) throws SQLException {
    //     User user = new User(null, null , null);
    //     while (resultSet.next()) {
    //         user.setEmail(resultSet.getString("email"));
    //         user.setPassword(resultSet.getString("password"));
    //         user.setUserName(resultSet.getString("username"));
    //     }
    //     return user;
    // }


    

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

    

   

    public User saveUser(User user) throws SQLException, RuntimeException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            
            conn = connectToDB();
            String sqlQuery = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            //db.update(preparedStatement, user.getUserName(), user.getPassword(), user.getEmail());
            preparedStatement.setString(1, user.getUsername());
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
            return new User(user.getUsername(), user.getPassword(), user.getEmail());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

        
    }

    public void deleteUser(User user) throws RuntimeException, SQLException{
        Connection conn = null;
        PreparedStatement preparedStatement = null;
    
        try {
            conn = connectToDB();
            String sqlQuery = "DELETE FROM User WHERE email = ?;";
            preparedStatement = conn.prepareStatement(sqlQuery);
            
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " not found");  
        }

        try {
            preparedStatement.close();
            conn.close();   
        } catch (RuntimeException e) {
            // do nothing
            }

    }


    public boolean isAdmin(User user) throws RuntimeException, SQLException {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User moderator = new User(null,null,null);

        

        try {
            conn = connectToDB();
            
            String sqlQuery = "SELECT User.email, User.password, User.username " +
            "FROM User " +
            "JOIN Moderator " + 
            "ON User.email = Moderator.email "+
            "WHERE Moderator.email = ?";

            
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                moderator.mapUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
            //throw new DuplicateUserException("User with email " + user.getEmail() + " already exists");   
        }

        try {
            resultSet.close();
            preparedStatement.close();
            conn.close();
                
        } catch (RuntimeException e) {
            // do nothing
            }

        if (moderator != null) {
            if (moderator.getPassword().equals(user.getPassword())) {
                return true;        
            }
        }
     
        return false;
        
    }

    public User login(User user) throws RuntimeException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String password = "";
        try  {
            conn = connectToDB();
            String sqlQuery = "SELECT password FROM User WHERE email = ?;";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, user.getEmail());
            resultSet = statement.executeQuery();

            
            while (resultSet.next()) {
                password = (resultSet.getString("password"));
            }
              
        } catch (SQLException e) {
            throw new SQLException(e);
            // throw new UserNotFoundException("User with email " + email + " not found");
            }

        if (password.equals(user.getPassword())) {
            try {
                return loadUser(user.getEmail());
            } catch (RuntimeException e) {
                throw new RuntimeException("User with email " + user.getEmail() + " did not match the password");
            }
        } 

        return null;

    }


    public static void main(String[] args) throws SQLException, RuntimeException {
        TestingWhileCoding t = new TestingWhileCoding();
        //t.doStuff();
        User user = new User("tobbtest1@test.com", "test", "tet3979w4");
        
        
        // User user1 = t.saveUser(new User( "tobbtest1@test.com", "test", "tet3979w4"));
        // t.deleteUser(new User( "tobbtest2@test.com", "test", "tet3979w4"));
        // User user = t.loadUser("toob@test.com");
        // System.out.println(user.toString());

        // test admin below
        // Boolean admin =t.isAdmin(new User("mod1@backpacking.com", "password", "moderator1"));
        // if (admin) {
        //     System.out.println("admin /////////////////////////////////////////");
        // } else {
        //     System.out.println("not admin -------------------------------------");
        // }

        // test login below
        User user3000 = t.login(user);
        System.out.println(user3000.toString());
        
    
    }
    
}
