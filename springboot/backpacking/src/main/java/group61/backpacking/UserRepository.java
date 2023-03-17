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
        String url = "jdbc:sqlite:bpAdvisorDatabase.db";

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
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        ResultSet resultSet = null;

        try {
            conn = connectToDB();

            String sqlQueryDupUsername = "SELECT COUNT(*) FROM User WHERE username = ?";
            preparedStatement1 = conn.prepareStatement(sqlQueryDupUsername);
            preparedStatement1.setString(1, user.getUsername());
            resultSet = preparedStatement1.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                throw new DuplicateUserException("Username " + user.getUsername() + " is already taken");
            }

            String sqlQuery = "INSERT INTO User (username, password, email) VALUES (?, ?, ?);";
            preparedStatement2 = conn.prepareStatement(sqlQuery);
            // db.update(preparedStatement, user.getUserName(), user.getPassword(),
            // user.getEmail());
            preparedStatement2.setString(1, user.getUsername());
            preparedStatement2.setString(2, user.getPassword());
            preparedStatement2.setString(3, user.getEmail());
            preparedStatement2.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
            // throw new DuplicateUserException("User with email " + user.getEmail() + "
            // already exists");
        } finally {
            if (preparedStatement1 != null) {
                preparedStatement1.close();
            } 
            if (preparedStatement2 != null) {
                preparedStatement2.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
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
        User user = new User(null, null, null);

        try {
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
        finally {
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (conn != null) {
                conn.close();
            }
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

        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    //////////////////////////////////////////////////////
    // other functionalities


    public User login(User user) throws RuntimeException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String password = "";
        try {
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
        finally {
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (conn != null) {
                conn.close();
            }
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

    public boolean isAdmin(String email) throws RuntimeException, SQLException {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Boolean isAdmin = false;

        try {
            conn = connectToDB();

            String sqlQuery = "SELECT *" +
                    "FROM Moderator " +
                    "WHERE email = ?";

            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isAdmin = true;
            }
        } catch (SQLException e) {
            throw new SQLException(e);
            // throw new DuplicateUserException("User with email " + user.getEmail() + "
            // already exists");
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return isAdmin;

    }

    

}
