package group61.backpacking;


import java.sql.*;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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
            // db.update(preparedStatement, user.getUserName(), user.getPassword(),
            // user.getEmail());
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
            // throw new DuplicateUserException("User with email " + user.getEmail() + "
            // already exists");
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
        User moderator = new User(null, null, null);

        try {
            conn = connectToDB();

            String sqlQuery = "SELECT User.email, User.password, User.username " +
                    "FROM User " +
                    "JOIN Moderator " +
                    "ON User.email = Moderator.email " +
                    "WHERE Moderator.email = ?";

            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, user.getEmail());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                moderator.mapUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
            // throw new DuplicateUserException("User with email " + user.getEmail() + "
            // already exists");
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

    public Date getDate() {
        // find out how to get current date

        return new Date(2020, 12, 12);

    }

    // Travel Route/ Itinerary//
   /*  public void saveItineraryDestination(User user, String title, String destination, Integer order,
            Itinerary itinerary) throws SQLException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            conn = connectToDB();
            String sqlQuery = "INSERT INTO itinerary_destination (itinerary_id, destination_name, order_number) VALUES (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            // db.update(preparedStatement, user.getUserName(), user.getPassword(),
            // user.getEmail());
            preparedStatement.setInt(1, itinerary.getId());
            preparedStatement.setString(2, destination);
            preparedStatement.setInt(3, order);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);

            // throw new DuplicateUserException("User with email " + user.getEmail() + "
            // already exists");
        }

        try {
            resultSet.close();
            statement.close();
            conn.close();

        } catch (RuntimeException e) {
            // do nothing
        }

    } */

    /* public Itinerary loadItineraryByInput(String title, String email) throws RuntimeException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Itinerary itinerary = new Itinerary(-1, null, null, -1, null, null, null, 0);

        try {
            conn = connectToDB();
            String sqlQuery = "SELECT * FROM Itinerary WHERE title == ? AND writer_email == ?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, title);
            statement.setString(2, email);
            resultSet = statement.executeQuery();

            System.out.println("loadItinerary 1");

            while (resultSet.next()) {

                itinerary.mapItineraryFromResultSet(resultSet);

            }

        } catch (SQLException e) {
            System.out.println("Error in loadItinerary   1");
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

        if (itinerary.getId() == -1) {
            return null;
        }

        return itinerary;
    } */

    /* public boolean validateItinerary(String title, String email) throws RuntimeException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Itinerary itinerary = new Itinerary(-1, null, null, -1, null, null, null);

        try {
            conn = connectToDB();
            String sqlQuery = "SELECT * FROM Itinerary WHERE title = ? AND writer_email = ?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, title);
            statement.setString(2, email);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                itinerary.mapItineraryFromResultSet(resultSet);
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
        if (itinerary.getId() == -1) {
            return true;
        }
        return false;
    } */

    // time to apropriate dataType
   /*  public void saveItinerary(User user, String estimatedTime, String description, String image, String title,
        List<String> destinationsList) throws SQLException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            if (validateItinerary(title, user.getEmail()) == false) {
                throw new SQLException("Itinerary with this title already exists");

            }

            conn = connectToDB();
            String sqlQuery = "INSERT INTO Itinerary ( writer_email, written_date, estimated_time, itinerary_description, image, title ) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            // db.update(preparedStatement, user.getUserName(), user.getPassword(),
            // user.getEmail());
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setDate(2, getDate());
            preparedStatement.setString(3, estimatedTime);
            preparedStatement.setString(4, description);
            preparedStatement.setString(5, image);
            preparedStatement.setString(6, title);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
            // throw new DuplicateUserException("User with email " + user.getEmail() + "
            // already exists");
        }

        try {
            resultSet.close();
            statement.close();
            conn.close();

        } catch (RuntimeException e) {
            // do nothing
        } */

       /*  Itinerary itinerary = new Itinerary(-1, null, null, -1, null, null, null);

        try {
            itinerary = loadItineraryByInput(title, user.getEmail());
        } catch (Exception e) {
            // do nothing
        }
        System.out.println(itinerary.toString());

        try {

            for (int i = 0; i < destinationsList.size(); i++) {
                saveItineraryDestination(user, title, destinationsList.get(i), i + 1, itinerary);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

    }

    // sletting basert på tittel og email
    public void deleteItinerary_byEmail(User user, String title) throws SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {

            conn = connectToDB();
            String sqlQuery = "DELETE FROM Itinerary WHERE title = ? AND writer_email = ?";
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, title);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        try {
            preparedStatement.close();
            conn.close();

        } catch (RuntimeException e) {
            // do nothing
        }

    } */

    /* public void deleteItinerary(Itinerary itinerary) throws RuntimeException, SQLException {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = connectToDB();
            String sqlQuery = "DELETE FROM Itinerary WHERE itinerary_description = ?;";
            preparedStatement = conn.prepareStatement(sqlQuery);

            preparedStatement.setString(1, itinerary.getTitle());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new UserNotFoundException("Itinerary with name " + itinerary.getTitle() + " not found");
        }

        try {
            preparedStatement.close();
            conn.close();
        } catch (RuntimeException e) {
        }
    } */

    /* public List<Itinerary> getItinerariesByUserEmail(String userEmail) throws RuntimeException, SQLException {
        List<Itinerary> itineraries = new ArrayList<>();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conn = connectToDB();
            String sqlQuery = "SELECT * FROM Itinerary WHERE writer_email = ?";
            preparedStatement = conn.prepareStatement(sqlQuery);

            preparedStatement.setString(1, userEmail);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Itinerary itinerary = new Itinerary(-1, null, null, -1, null, null, null); // TODO: kostruktør i
                                                                                           // Itinerary
                itinerary.mapItineraryFromResultSet(resultSet);
                // TODO: Legge til destinations
                itineraries.add(itinerary);
            }

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                // handle exception
            }
        }
        return itineraries;
    } */

}
