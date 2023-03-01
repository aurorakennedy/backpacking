package group61.backpacking;


import java.sql.*;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Repository
public class UserRepository {


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

    //////////////////////////////////////////////////////
    // save functions

    public User saveUser(User user) throws SQLException, RuntimeException {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conn = connectToDB();

            String sqlQueryDupUsername = "SELECT COUNT(*) FROM User WHERE username = ?";
            preparedStatement = conn.prepareStatement(sqlQueryDupUsername);
            preparedStatement.setString(1, user.getUsername());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                throw new DuplicateUserException("Username " + user.getUsername() + " is already taken");
            }

            String sqlQuery = "INSERT INTO User (username, password, email) VALUES (?, ?, ?);";
            preparedStatement = conn.prepareStatement(sqlQuery);
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
            preparedStatement.close();
            conn.close();   
        } catch (RuntimeException e) {
            // do nothing
            }
            
        try {
            return loadUser(user.getEmail());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    //////////////////////////////////////////////////////
    // load functions

    public User loadUser(String email) throws RuntimeException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = new User(null,null,null);

        try  {
            conn = connectToDB();
            String sqlQuery = "SELECT * FROM User WHERE email = ?;";
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


    //////////////////////////////////////////////////////
    // update functions

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

    //////////////////////////////////////////////////////
    // other functionalities


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


    


}
