package group61.backpacking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.InputStream;
import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Playground {

    public Playground() {
    }

    public static Connection connectToDB() {
        Connection conn = null;
        String url = "jdbc:sqlite:sqlitesample.db";
        // "BACKPACKING\sqlitesample.db"
        // String url = "jdbc:sqlite:/../../../sqlitesample.db";

        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite database established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public User loadUser(String email) throws RuntimeException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User user = new User(null, null, null);

        try {
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

    public void saveDestination(String destName, String country, String destDescription) throws SQLException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            conn = connectToDB();
            String sqlQuery = "INSERT INTO Destinations (destination_name, country, destination_description) VALUES (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            // db.update(preparedStatement, user.getUserName(), user.getPassword(),
            // user.getEmail());
            preparedStatement.setString(1, destName);
            preparedStatement.setString(2, country);
            preparedStatement.setString(3, destDescription);

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

    }

    public void saveCountry(String countryName, String continent) throws SQLException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            saveContinent(continent);
        } catch (Exception e) {
            // Do nothing
        }

        try {

            conn = connectToDB();
            String sqlQuery = "INSERT INTO Countries (country_name, continent) VALUES (?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            // db.update(preparedStatement, user.getUserName(), user.getPassword(),
            // user.getEmail());
            preparedStatement.setString(1, countryName);
            preparedStatement.setString(2, continent);

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
    }

    public void saveContinent(String continent) throws SQLException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {

            conn = connectToDB();
            String sqlQuery = "INSERT INTO Continents (continent_name) VALUES (?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            // db.update(preparedStatement, user.getUserName(), user.getPassword(),
            // user.getEmail());
            preparedStatement.setString(1, continent);

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

    }

    public Date getDate() {
        // find out how to get current date

        return new Date(2020, 12, 12);

    }

    public void saveItineraryDestination(User user, String title, String destination, Integer order,
            Itinerary itinerary) throws SQLException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            conn = connectToDB();
            String sqlQuery = "INSERT INTO itinerary_destination (itinerary_id, destination_name, order) VALUES (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            // db.update(preparedStatement, user.getUserName(), user.getPassword(),
            // user.getEmail());
            preparedStatement.setInt(1, itinerary.getId());
            preparedStatement.setString(2, destination);
            preparedStatement.setInt(3, order);
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

    }

    public Itinerary loadItineraryByInput(String title, String email) throws RuntimeException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Itinerary itinerary = new Itinerary(-1, null, null, null, null, null, null);

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

        return itinerary;
    }

    public boolean validateItinerary(String title, String email) throws RuntimeException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Itinerary itinerary = new Itinerary(-1, null, null, null, null, null, null);

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
    }

    // time to apropriate dataType
    public void saveItinerary(User user, String estimatedTime, String description, InputStream image, String title,
            List<String> destinationsList) throws SQLException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            if (validateItinerary(title, user.getEmail()) == false) {
                throw new SQLException("Itinerary with title " + title + " already exists");
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
            preparedStatement.setBinaryStream(5, image);
            preparedStatement.setString(6, title);
            preparedStatement.setBinaryStream(5, image);
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
        }

        Itinerary itinerary = new Itinerary(-1, null, null, null, null, null, null);

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
        } catch (Exception e) {
            // do nothing
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
            resultSet.close();
            statement.close();
            conn.close();

        } catch (RuntimeException e) {
            // do nothing
        }

        try {
            // return loadUser(user.getEmail());
            return new User(user.getUsername(), user.getPassword(), user.getEmail());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteUser(User user) throws RuntimeException, SQLException {
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

    public static void main(String[] args) throws SQLException, RuntimeException {
        Playground t = new Playground();
        // t.doStuff();
        User user = new User("tobbtest1@test.com", "test", "tet3979w4");

        // test itinerarystuff below: //////////////////////////////////////
        List<String> destinationsList = Arrays.asList("Oslo", "Bergen", "Trondheim");
        t.saveItinerary(user, "11.2", "a cool trip", null, "cool trip1", destinationsList);

        // System.out.println( t.loadItineraryByInput("cool trip2",
        // "tobbtest1@test.com").toString()
        // );

        // t.saveContinent("Europe");
        // t.saveContinent("Asia");
        // t.saveContinent("North America");
        // t.saveContinent("South America");
        // t.saveContinent("Africa");
        // t.saveContinent("Oceania");
        // t.saveContinent("Oceania");
        // t.saveContinent("Antarctica");

        // t.saveCountry("Afghanistan", "Asia");
        // t.saveCountry("Bahrain", "Asia");
        // t.saveCountry("Bangladesh", "Asia");
        // t.saveCountry("Bhutan", "Asia");
        // t.saveCountry("Brunei", "Asia");
        // t.saveCountry("Cambodia", "Asia");
        // t.saveCountry("China", "Asia");
        // t.saveCountry("Georgia", "Asia");
        // t.saveCountry("India", "Asia");
        // t.saveCountry("Indonesia", "Asia");
        // t.saveCountry("Iran", "Asia");
        // t.saveCountry("Iraq", "Asia");
        // t.saveCountry("Israel", "Asia");
        // t.saveCountry("Japan", "Asia");
        // t.saveCountry("Jordan", "Asia");
        // t.saveCountry("Kazakhstan", "Asia");
        // t.saveCountry("Kuwait", "Asia");
        // t.saveCountry("Kyrgyzstan", "Asia");
        // t.saveCountry("Laos", "Asia");
        // t.saveCountry("Lebanon", "Asia");
        // t.saveCountry("Malaysia", "Asia");
        // t.saveCountry("Maldives", "Asia");
        // t.saveCountry("Mongolia", "Asia");
        // t.saveCountry("Myanmar", "Asia");
        // t.saveCountry("Nepal", "Asia");
        // t.saveCountry("North Korea", "Asia");
        // t.saveCountry("Oman", "Asia");
        // t.saveCountry("Pakistan", "Asia");
        // t.saveCountry("Palestine", "Asia");
        // t.saveCountry("Philippines", "Asia");
        // t.saveCountry("Qatar", "Asia");
        // t.saveCountry("Saudi Arabia", "Asia");
        // t.saveCountry("Singapore", "Asia");
        // t.saveCountry("South Korea", "Asia");
        // t.saveCountry("Sri Lanka", "Asia");
        // t.saveCountry("Syria", "Asia");
        // t.saveCountry("Taiwan", "Asia");
        // t.saveCountry("Tajikistan", "Asia");
        // t.saveCountry("Thailand", "Asia");
        // t.saveCountry("Timor-Leste", "Asia");
        // t.saveCountry("Turkey", "Asia");
        // t.saveCountry("Turkmenistan", "Asia");
        // t.saveCountry("United Arab Emirates", "Asia");
        // t.saveCountry("Uzbekistan", "Asia");
        // t.saveCountry("Vietnam", "Asia");
        // t.saveCountry("Yemen", "Asia");

        // t.saveCountry("Algeria", "Africa");
        // t.saveCountry("Angola", "Africa");
        // t.saveCountry("Benin", "Africa");
        // t.saveCountry("Botswana", "Africa");
        // t.saveCountry("Burkina Faso", "Africa");
        // t.saveCountry("Burundi", "Africa");
        // t.saveCountry("Cabo Verde", "Africa");
        // t.saveCountry("Cameroon", "Africa");
        // t.saveCountry("Central African Republic", "Africa");
        // t.saveCountry("Chad", "Africa");
        // t.saveCountry("Comoros", "Africa");
        // t.saveCountry("Congo", "Africa");
        // t.saveCountry("Cote d'Ivoire", "Africa");
        // t.saveCountry("Djibouti", "Africa");
        // t.saveCountry("Egypt", "Africa");
        // t.saveCountry("Equatorial Guinea", "Africa");
        // t.saveCountry("Eritrea", "Africa");
        // t.saveCountry("Eswatini", "Africa");
        // t.saveCountry("Ethiopia", "Africa");
        // t.saveCountry("Gabon", "Africa");
        // t.saveCountry("Gambia", "Africa");
        // t.saveCountry("Ghana", "Africa");
        // t.saveCountry("Guinea", "Africa");
        // t.saveCountry("Guinea-Bissau", "Africa");
        // t.saveCountry("Kenya", "Africa");
        // t.saveCountry("Lesotho", "Africa");
        // t.saveCountry("Liberia", "Africa");
        // t.saveCountry("Libya", "Africa");
        // t.saveCountry("Madagascar", "Africa");
        // t.saveCountry("Malawi", "Africa");
        // t.saveCountry("Mali", "Africa");
        // t.saveCountry("Mauritania", "Africa");
        // t.saveCountry("Mauritius", "Africa");
        // t.saveCountry("Morocco", "Africa");
        // t.saveCountry("Mozambique", "Africa");
        // t.saveCountry("Namibia", "Africa");
        // t.saveCountry("Niger", "Africa");
        // t.saveCountry("Nigeria", "Africa");
        // t.saveCountry("Rwanda", "Africa");
        // t.saveCountry("Sao Tome and Principe", "Africa");
        // t.saveCountry("Senegal", "Africa");
        // t.saveCountry("Seychelles", "Africa");
        // t.saveCountry("Sierra Leone", "Africa");
        // t.saveCountry("Somalia", "Africa");
        // t.saveCountry("South Africa", "Africa");
        // t.saveCountry("South Sudan", "Africa");
        // t.saveCountry("Sudan", "Africa");
        // t.saveCountry("Tanzania", "Africa");
        // t.saveCountry("Togo", "Africa");
        // t.saveCountry("Tunisia", "Africa");
        // t.saveCountry("Uganda", "Africa");
        // t.saveCountry("Zambia", "Africa");
        // t.saveCountry("Zimbabwe", "Africa");

        // t.saveCountry("Albania", "Europe");
        // t.saveCountry("Andorra", "Europe");
        // t.saveCountry("Austria", "Europe");
        // t.saveCountry("Belarus", "Europe");
        // t.saveCountry("Belgium", "Europe");
        // t.saveCountry("Bosnia and Herzegovina", "Europe");
        // t.saveCountry("Bulgaria", "Europe");
        // t.saveCountry("Croatia", "Europe");
        // t.saveCountry("Cyprus", "Europe");
        // t.saveCountry("Czech Republic", "Europe");
        // t.saveCountry("Denmark", "Europe");
        // t.saveCountry("Estonia", "Europe");

        // t.saveCountry("Afghanistan", "Asia");
        // t.saveCountry("Bahrain", "Asia");
        // t.saveCountry("Bangladesh", "Asia");
        // t.saveCountry("Bhutan", "Asia");
        // t.saveCountry("Brunei", "Asia");
        // t.saveCountry("Cambodia", "Asia");
        // t.saveCountry("China", "Asia");
        // t.saveCountry("Georgia", "Asia");
        // t.saveCountry("India", "Asia");
        // t.saveCountry("Indonesia", "Asia");
        // t.saveCountry("Iran", "Asia");
        // t.saveCountry("Iraq", "Asia");
        // t.saveCountry("Israel", "Asia");
        // t.saveCountry("Japan", "Asia");
        // t.saveCountry("Jordan", "Asia");
        // t.saveCountry("Kazakhstan", "Asia");
        // t.saveCountry("Kuwait", "Asia");
        // t.saveCountry("Kyrgyzstan", "Asia");
        // t.saveCountry("Laos", "Asia");
        // t.saveCountry("Lebanon", "Asia");
        // t.saveCountry("Malaysia", "Asia");
        // t.saveCountry("Maldives", "Asia");
        // t.saveCountry("Mongolia", "Asia");
        // t.saveCountry("Myanmar", "Asia");
        // t.saveCountry("Nepal", "Asia");
        // t.saveCountry("North Korea", "Asia");
        // t.saveCountry("Oman", "Asia");
        // t.saveCountry("Pakistan", "Asia");
        // t.saveCountry("Palestine", "Asia");
        // t.saveCountry("Philippines", "Asia");
        // t.saveCountry("Qatar", "Asia");
        // t.saveCountry("Saudi Arabia", "Asia");
        // t.saveCountry("Singapore", "Asia");
        // t.saveCountry("South Korea", "Asia");
        // t.saveCountry("Sri Lanka", "Asia");
        // t.saveCountry("Syria", "Asia");
        // t.saveCountry("Taiwan", "Asia");
        // t.saveCountry("Tajikistan", "Asia");
        // t.saveCountry("Thailand", "Asia");
        // t.saveCountry("Timor-Leste", "Asia");
        // t.saveCountry("Turkey", "Asia");
        // t.saveCountry("Turkmenistan", "Asia");
        // t.saveCountry("United Arab Emirates", "Asia");
        // t.saveCountry("Uzbekistan", "Asia");
        // t.saveCountry("Vietnam", "Asia");
        // t.saveCountry("Yemen", "Asia");

        // t.saveCountry("Algeria", "Africa");
        // t.saveCountry("Angola", "Africa");
        // t.saveCountry("Benin", "Africa");
        // t.saveCountry("Botswana", "Africa");
        // t.saveCountry("Burkina Faso", "Africa");
        // t.saveCountry("Burundi", "Africa");
        // t.saveCountry("Cabo Verde", "Africa");
        // t.saveCountry("Cameroon", "Africa");
        // t.saveCountry("Central African Republic", "Africa");
        // t.saveCountry("Chad", "Africa");
        // t.saveCountry("Comoros", "Africa");
        // t.saveCountry("Congo", "Africa");
        // t.saveCountry("Cote d'Ivoire", "Africa");
        // t.saveCountry("Djibouti", "Africa");
        // t.saveCountry("Egypt", "Africa");
        // t.saveCountry("Equatorial Guinea", "Africa");
        // t.saveCountry("Eritrea", "Africa");
        // t.saveCountry("Eswatini", "Africa");
        // t.saveCountry("Ethiopia", "Africa");
        // t.saveCountry("Gabon", "Africa");
        // t.saveCountry("Gambia", "Africa");
        // t.saveCountry("Ghana", "Africa");
        // t.saveCountry("Guinea", "Africa");
        // t.saveCountry("Guinea-Bissau", "Africa");
        // t.saveCountry("Kenya", "Africa");
        // t.saveCountry("Lesotho", "Africa");
        // t.saveCountry("Liberia", "Africa");
        // t.saveCountry("Libya", "Africa");
        // t.saveCountry("Madagascar", "Africa");
        // t.saveCountry("Malawi", "Africa");
        // t.saveCountry("Mali", "Africa");
        // t.saveCountry("Mauritania", "Africa");
        // t.saveCountry("Mauritius", "Africa");
        // t.saveCountry("Morocco", "Africa");
        // t.saveCountry("Mozambique", "Africa");
        // t.saveCountry("Namibia", "Africa");
        // t.saveCountry("Niger", "Africa");
        // t.saveCountry("Nigeria", "Africa");
        // t.saveCountry("Rwanda", "Africa");
        // t.saveCountry("Sao Tome and Principe", "Africa");
        // t.saveCountry("Senegal", "Africa");
        // t.saveCountry("Seychelles", "Africa");
        // t.saveCountry("Sierra Leone", "Africa");
        // t.saveCountry("Somalia", "Africa");
        // t.saveCountry("South Africa", "Africa");
        // t.saveCountry("South Sudan", "Africa");
        // t.saveCountry("Sudan", "Africa");
        // t.saveCountry("Tanzania", "Africa");
        // t.saveCountry("Togo", "Africa");
        // t.saveCountry("Tunisia", "Africa");
        // t.saveCountry("Uganda", "Africa");
        // t.saveCountry("Zambia", "Africa");
        // t.saveCountry("Zimbabwe", "Africa");

        // t.saveCountry("Albania", "Europe");
        // t.saveCountry("Andorra", "Europe");
        // t.saveCountry("Austria", "Europe");
        // t.saveCountry("Belarus", "Europe");
        // t.saveCountry("Belgium", "Europe");
        // t.saveCountry("Bosnia and Herzegovina", "Europe");
        // t.saveCountry("Bulgaria", "Europe");
        // t.saveCountry("Croatia", "Europe");
        // t.saveCountry("Cyprus", "Europe");
        // t.saveCountry("Czech Republic", "Europe");
        // t.saveCountry("Denmark", "Europe");
        // t.saveCountry("Estonia", "Europe");
        // t.saveCountry("Finland", "Europe");
        // t.saveCountry("France", "Europe");
        // t.saveCountry("Germany", "Europe");
        // t.saveCountry("Greece", "Europe");
        // t.saveCountry("Hungary", "Europe");
        // t.saveCountry("Iceland", "Europe");
        // t.saveCountry("Ireland", "Europe");
        // t.saveCountry("Greece", "Europe");
        // t.saveCountry("Hungary", "Europe");
        // t.saveCountry("Iceland", "Europe");
        // t.saveCountry("Ireland", "Europe");
        // t.saveCountry("Italy", "Europe");
        // t.saveCountry("Kosovo", "Europe");
        // t.saveCountry("Latvia", "Europe");
        // t.saveCountry("Liechtenstein", "Europe");
        // t.saveCountry("Lithuania", "Europe");
        // t.saveCountry("Luxembourg", "Europe");
        // t.saveCountry("Malta", "Europe");
        // t.saveCountry("Moldova", "Europe");
        // t.saveCountry("Monaco", "Europe");
        // t.saveCountry("Montenegro", "Europe");
        // t.saveCountry("Netherlands", "Europe");
        // t.saveCountry("North Macedonia", "Europe");
        // t.saveCountry("Norway", "Europe");
        // t.saveCountry("Poland", "Europe");
        // t.saveCountry("Kosovo", "Europe");
        // t.saveCountry("Latvia", "Europe");
        // t.saveCountry("Liechtenstein", "Europe");
        // t.saveCountry("Lithuania", "Europe");
        // t.saveCountry("Luxembourg", "Europe");
        // t.saveCountry("Malta", "Europe");
        // t.saveCountry("Moldova", "Europe");
        // t.saveCountry("Monaco", "Europe");
        // t.saveCountry("Montenegro", "Europe");
        // t.saveCountry("Netherlands", "Europe");
        // t.saveCountry("North Macedonia", "Europe");
        // t.saveCountry("Norway", "Europe");
        // t.saveCountry("Poland", "Europe");
        // t.saveCountry("Portugal", "Europe");
        // t.saveCountry("Romania", "Europe");
        // t.saveCountry("Romania", "Europe");
        // t.saveCountry("Russia", "Europe");
        // t.saveCountry("San Marino", "Europe");
        // t.saveCountry("Serbia", "Europe");
        // t.saveCountry("San Marino", "Europe");
        // t.saveCountry("Serbia", "Europe");
        // t.saveCountry("Slovakia", "Europe");
        // t.saveCountry("Slovenia", "Europe");
        // t.saveCountry("Spain", "Europe");
        // t.saveCountry("Sweden", "Europe");
        // t.saveCountry("Switzerland", "Europe");
        // t.saveCountry("Ukraine", "Europe");
        // t.saveCountry("United Kingdom", "Europe");
        // t.saveCountry("Vatican City", "Europe");

        // t.saveCountry("Slovenia", "Europe");
        // t.saveCountry("Spain", "Europe");
        // t.saveCountry("Sweden", "Europe");
        // t.saveCountry("Switzerland", "Europe");
        // t.saveCountry("Ukraine", "Europe");
        // t.saveCountry("United Kingdom", "Europe");
        // t.saveCountry("Vatican City", "Europe");

        // t.saveCountry("Argentina", "South America");
        // t.saveCountry("Bolivia", "South America");
        // t.saveCountry("Brazil", "South America");
        // t.saveCountry("Chile", "South America");
        // t.saveCountry("Bolivia", "South America");
        // t.saveCountry("Brazil", "South America");
        // t.saveCountry("Chile", "South America");
        // t.saveCountry("Colombia", "South America");
        // t.saveCountry("Ecuador", "South America");
        // t.saveCountry("Guyana", "South America");
        // t.saveCountry("Guyana", "South America");
        // t.saveCountry("Paraguay", "South America");
        // t.saveCountry("Peru", "South America");
        // t.saveCountry("Suriname", "South America");
        // t.saveCountry("Peru", "South America");
        // t.saveCountry("Suriname", "South America");
        // t.saveCountry("Uruguay", "South America");
        // t.saveCountry("Venezuela", "South America");

        // t.saveCountry("Antigua and Barbuda", "North America");
        // t.saveCountry("Bahamas", "North America");
        // t.saveCountry("Barbados", "North America");
        // t.saveCountry("Belize", "North America");
        // t.saveCountry("Canada", "North America");
        // t.saveCountry("Costa Rica", "North America");
        // t.saveCountry("Cuba", "North America");
        // t.saveCountry("Dominica", "North America");
        // t.saveCountry("Dominican Republic", "North America");
        // t.saveCountry("El Salvador", "North America");
        // t.saveCountry("Grenada", "North America");
        // t.saveCountry("Guatemala", "North America");
        // t.saveCountry("Haiti", "North America");
        // t.saveCountry("Honduras", "North America");
        // t.saveCountry("Jamaica", "North America");
        // t.saveCountry("Mexico", "North America");
        // t.saveCountry("Nicaragua", "North America");
        // t.saveCountry("Panama", "North America");
        // t.saveCountry("Saint Kitts and Nevis", "North America");
        // t.saveCountry("Saint Lucia", "North America");
        // t.saveCountry("Saint Vincent and the Grenadines", "North America");
        // t.saveCountry("Trinidad and Tobago", "North America");
        // t.saveCountry("United States of America", "North America");

        // t.saveCountry("Australia", "Oceania");
        // t.saveCountry("Fiji", "Oceania");
        // t.saveCountry("Kiribati", "Oceania");
        // t.saveCountry("Marshall Islands", "Oceania");
        // t.saveCountry("Micronesia", "Oceania");
        // t.saveCountry("Nauru", "Oceania");
        // t.saveCountry("New Zealand", "Oceania");
        // t.saveCountry("Palau", "Oceania");
        // t.saveCountry("Papua New Guinea", "Oceania");
        // t.saveCountry("Samoa", "Oceania");
        // t.saveCountry("Solomon Islands", "Oceania");
        // t.saveCountry("Tonga", "Oceania");
        // t.saveCountry("Tuvalu", "Oceania");
        // t.saveCountry("Vanuatu", "Oceania");

        // t.saveCountry("Venezuela", "South America");

        // t.saveCountry("Antigua and Barbuda", "North America");
        // t.saveCountry("Bahamas", "North America");
        // t.saveCountry("Barbados", "North America");
        // t.saveCountry("Belize", "North America");
        // t.saveCountry("Canada", "North America");
        // t.saveCountry("Costa Rica", "North America");
        // t.saveCountry("Cuba", "North America");
        // t.saveCountry("Dominica", "North America");
        // t.saveCountry("Dominican Republic", "North America");
        // t.saveCountry("El Salvador", "North America");
        // t.saveCountry("Grenada", "North America");
        // t.saveCountry("Guatemala", "North America");
        // t.saveCountry("Haiti", "North America");
        // t.saveCountry("Honduras", "North America");
        // t.saveCountry("Jamaica", "North America");
        // t.saveCountry("Mexico", "North America");
        // t.saveCountry("Nicaragua", "North America");
        // t.saveCountry("Panama", "North America");
        // t.saveCountry("Saint Kitts and Nevis", "North America");
        // t.saveCountry("Saint Lucia", "North America");
        // t.saveCountry("Saint Vincent and the Grenadines", "North America");
        // t.saveCountry("Trinidad and Tobago", "North America");
        // t.saveCountry("United States of America", "North America");

        // t.saveCountry("Australia", "Oceania");
        // t.saveCountry("Fiji", "Oceania");
        // t.saveCountry("Kiribati", "Oceania");
        // t.saveCountry("Marshall Islands", "Oceania");
        // t.saveCountry("Micronesia", "Oceania");
        // t.saveCountry("Nauru", "Oceania");
        // t.saveCountry("New Zealand", "Oceania");
        // t.saveCountry("Palau", "Oceania");
        // t.saveCountry("Papua New Guinea", "Oceania");
        // t.saveCountry("Samoa", "Oceania");
        // t.saveCountry("Solomon Islands", "Oceania");
        // t.saveCountry("Tonga", "Oceania");
        // t.saveCountry("Tuvalu", "Oceania");
        // t.saveCountry("Vanuatu", "Oceania");

        // t.saveDestination("Oslo", "Norway", "The capital of Norway. Oslo is a city of
        // contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a
        // city of green spaces, with more than 50 percent of the city covered by forest
        // and parkland. Oslo is a city of contrasts, with a rich cultural life and a
        // vibrant nightlife. Oslo is also a city of green spaces, with more than 50
        // percent of the city covered by forest and parkland. Oslo is a city of
        // contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a
        // city of green spaces, with more than 50 percent of the city covered by forest
        // and parkland. Oslo is a city of contrasts, with a rich cultural life and a
        // vibrant nightlife. Oslo is also a city of green spaces, with more than 50
        // percent of the city covered by forest and parkland. Oslo is a city of
        // contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a
        // city of green spaces, with more than 50 percent of the city covered by forest
        // and parkland. Oslo is a city of contrasts, with a rich cultural life and a
        // vibrant nightlife. Oslo is also a city of green spaces, with more than 50
        // percent of the city covered by forest and parkland. Oslo is a city of
        // contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a
        // city of green spaces, with more than 50 percent of the city covered by forest
        // and parkland. Oslo is a city of contrasts, with a rich cultural life and a
        // vibrant nightlife. Oslo is also a city of green spaces, with more than 50
        // percent of the city covered by forest and parkland. Oslo is a city of
        // contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a
        // city of green spaces, with more than 50 percent of the city covered by forest
        // and parkland. Oslo is a city of contrasts, with a rich cultural life and a
        // vibrant nightlife. Oslo is also a city of green spaces, with more than 50
        // percent of the city covered by forest and parkland. Oslo is a city of
        // contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a
        // city of green spaces, with more than 50 percent of the city covered by forest
        // and parkland. Oslo is a city of contrasts, with a rich cultural life and a
        // vibrant nightlife. Oslo is also a city of green spaces, with more than 50
        // percent of the city covered by forest");
        // t.saveDestination("Bergen", "Norway", "Bergen is a city and municipality in
        // Hordaland on the west coast of Norway. Bergen is the second-largest city in
        // Norway, the municipality covers 465 square kilometres (180 sq mi) and is home
        // to 278,121 inhabitants. Bergen is the administrative centre of Hordaland and
        // consists of eight boroughs: Arna, Bergenhus, Fana, Fyllingsdalen, Laksevåg,
        // Ytrebygda, Årstad and Åsane. The city centre and northern neighbourhoods are
        // on Byfjorden, the city's southern neighbourhoods are on the island of
        // Bergenøya, and the western neighbourhoods are on the peninsula of
        // Bergenshalvøyen. The city is an international centre for aquaculture,
        // shipping, offshore petroleum industry and subsea technology, and a national
        // centre for higher education, media, tourism and finance. Bergen Port is the
        // busiest in Norway and one of the largest in Northern Europe. The city is an
        // important centre for industries and maritime trade in Europe. ");
        // t.saveDestination("Trondheim", "Norway", "Trondheim is a city and
        // municipality in Sør-Trøndelag county, Norway. It has a population of 187,353
        // (as of 1 January 2018) and is the third most populous municipality in Norway.
        // The city functions as the administrative centre of Sør-Trøndelag county. The
        // municipality is the third largest by population in Norway and the fourth
        // largest municipal area. Trondheim lies on the south shore of the Trondheim
        // Fjord at the mouth of the river Nidelva. The settlement was founded in 997 as
        // a trading post and became the capital of Norway in 1130. The city was named
        // Strindheim in the early 12th century after the farm Strinda, which was the
        // residence of the bishop. The name Trondheim was used first in 1217 and is
        // derived from the Old Norse words trönd and heimr. The city's name therefore
        // translates to crossing place(s) of the fjord(s).");
        // t.saveDestination("Stavanger", "Norway", "Stavanger is a city and
        // municipality in Norway. It is the fourth-largest city in the country, with a
        // population of 122,388 as of 1 January 2018. The municipality is the third
        // largest by population in Norway and the fourth largest municipal area. The
        // city is the administrative centre of Rogaland county. Stavanger is the centre
        // of the Stavanger metropolitan region, which has a population of 279,000. The
        // city is an international centre for the oil industry and shipping, and a
        // national centre for aquaculture, higher education, tourism and finance.
        // Stavanger is the administrative centre of Rogaland county. The city is the
        // centre of the Stavanger metropolitan region, which has a population of
        // 279,000. The city is an international centre for the oil industry and
        // shipping, and a national centre for aquaculture, higher education, tourism
        // and finance. Stavanger is the administrative centre of Rogaland county. The
        // city is the centre of the Stavanger metropolitan region, which has a
        // population of 279,000. The city is an international centre for the oil
        // industry and shipping, and a national centre for aquaculture, higher
        // education, tourism and finance. Stavanger is the administrative centre of
        // Rogaland county. The city is the centre of the Stavanger metropolitan region,
        // which has a population of 279,000. The city is an international centre for
        // the oil industry and shipping, and a national centre for aquaculture, higher
        // education, tourism and finance. Stavanger is the administrative centre of
        // Rogaland county. The city is the centre of the Stavanger metropolitan region,
        // which has a population of 279,000. The city is an international centre for
        // the oil industry and shipping, and a national centre for aquaculture, higher
        // education, tourism and finance. Stavanger is the administrative centre of
        // Rogaland county. The city is the centre of the Stavanger metropolitan region,
        // which has a population of 279,000. The city is an international centre for
        // the oil industry and shipping, and a national centre for aquaculture, higher
        // education, tourism and finance. Stavanger is the administrative centre of
        // Rogaland county. The city is the centre of the Stavanger metropolitan region,
        // which has a population of 279,000. The city is an international centre for
        // the oil industry and shipping, and a national");
        // t.saveDestination("Tromsø", "Norway", "Tromsø is a city and municipality in
        // Troms county, Norway. It is the most populous city in Northern Norway. The
        // administrative centre of Troms county, Tromsø lies on the northern coast of
        // the island of Tromsøya, about 350 kilometres (220 mi) north of the Arctic
        // Circle. The city is the largest settlement in the county, and the 11th most
        // populous urban area in Norway. Tromsø has a population of 69,515 (as of 1
        // January 2018), and is the fourth most populous urban area in the country. The
        // city is a major centre for maritime industries and shipping, and is the
        // location of the Arctic University of Norway. Tromsø is also a popular tourist
        // destination, and is known for its northern lights, midnight sun, and the
        // annual Tromsø International Film Festival. Tromsø is a city and municipality
        // in Troms county, Norway. It is the most populous city in Northern Norway. The
        // administrative centre of Troms county, Tromsø lies on the northern coast of
        // the island of Tromsøya, about 350 kilometres (220 mi) north of the Arctic
        // Circle. The city is the largest settlement in the county, and the 11th most
        // populous urban area in Norway. Tromsø has a population of 69,515 (as of 1
        // January 2018), and is the fourth most populous urban area in the country. The
        // city is a major centre for maritime industries and shipping, and is the
        // location of the Arctic University of Norway. Tromsø is also a popular tourist
        // destination, and is known for its northern lights, midnight sun, and the
        // annual Tromsø International Film Festival. Tromsø is a city and municipality
        // in Troms county, Norway. It is the most populous city in Northern Norway. The
        // administrative centre of Troms county, Tromsø lies on the northern coast of
        // the island of Tromsøya, about 350 kilometres (220 mi) north of the Arctic
        // Circle. The city is the largest settlement in the county, and the 11th most
        // populous urban area in Norway. Tromsø has a population of 69,515 (as of 1
        // January 2018), and is the fourth most populous urban area in the country. The
        // city is a major centre for maritime industries and shipping, and is the
        // location of the Arctic University of Norway.");
        // t.saveDestination("spiterstulen", "Norway", "Spiterstulen is located 1111
        // meters above sea level in the lush Visdalen in Jotunheimen, and is a natural
        // starting point for trips to Norway's highest mountain - Galdhøpiggen. Here
        // you can also reach 17 of the 26 peaks in Norway at over 2,300 meters above
        // sea level on a day trip. Big brother Galdhøpiggen reigns in the west with
        // little brother Glittertind in the east. When the cabin opens in the spring,
        // the area is an eldorado for top ski tours.");
        // t.saveDestination("Gjendebu", "Norway", "Gjendebu is a mountain cabin in the
        // Jotunheimen National Park, Norway. It is located at 1,200 metres (3,900 ft)
        // above sea level, on the south side of the Gjende lake, in the Gjende valley.
        // The cabin is open from May to October. The cabin is located at the end of the
        // Gjendebu road, which is open to cars from the end of May to the end of
        // September. The cabin is located at the end of the Gjendebu road, which is
        // open to cars from the end of May to the end of September. The cabin is
        // located at the end of the Gjendebu road, which is open to cars from the end
        // of May to the end of September. The cabin is located at the end of the
        // Gjendebu road, which is open to cars from the end of May to the end of
        // September. The cabin is located at the end of the Gjendebu road, which is
        // open to cars from the end of May to the end of September. The cabin is
        // located at the end of the Gjendebu road, which is open to cars from the end
        // of May to the end of September. The cabin is located at the end of the
        // Gjendebu road, which is open to cars from the end of May to the end of
        // September. The cabin is located at the end of the Gjendebu road, which is
        // open to cars from the end of May to the end of September. The cabin is
        // located at the end of the Gjendebu road, which is open to cars from the end
        // of May to the end of September. The cabin is located at the end of the
        // Gjendebu road, which is open to cars from the end of May to the end of
        // September. The cabin is located at the end of the Gjendebu road, which is
        // open to cars from the end of May to the end of September. The cabin is
        // located at the end of the Gjendebu road, which is open to cars from the end
        // of May to the end of September. The cabin is located at the end of the
        // Gjendebu road, which is open to cars from the end of May to the end of
        // September. The cabin is located at the end of the Gjendebu road,");
        // t.saveDestination("Finse", "Norway", "Finse is a mountain village area on the
        // shore of the lake Finsevatnet in Ulvik municipality in Vestland county,
        // Norway. The village is centered on Finse Station, a railway station on the
        // Bergen Line. The village sits at an elevation of 1,222 metres (4,009 ft)
        // above sea level, making it the highest station on the entire Norwegian
        // railway system. ");
        // t.saveDestination("Oslo", "Norway", "The capital of Norway. Oslo is a city of
        // contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a
        // city of green spaces, with more than 50 percent of the city covered by forest
        // and parkland. Oslo is a city of contrasts, with a rich cultural life and a
        // vibrant nightlife. Oslo is also a city of green spaces, with more than 50
        // percent of the city covered by forest and parkland. Oslo is a city of
        // contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a
        // city of green spaces, with more than 50 percent of the city covered by forest
        // and parkland. Oslo is a city of contrasts, with a rich cultural life and a
        // vibrant nightlife. Oslo is also a city of green spaces, with more than 50
        // percent of the city covered by forest and parkland. Oslo is a city of
        // contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a
        // city of green spaces, with more than 50 percent of the city covered by forest
        // and parkland. Oslo is a city of contrasts, with a rich cultural life and a
        // vibrant nightlife. Oslo is also a city of green spaces, with more than 50
        // percent of the city covered by forest and parkland. Oslo is a city of
        // contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a
        // city of green spaces, with more than 50 percent of the city covered by forest
        // and parkland. Oslo is a city of contrasts, with a rich cultural life and a
        // vibrant nightlife. Oslo is also a city of green spaces, with more than 50
        // percent of the city covered by forest and parkland. Oslo is a city of
        // contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a
        // city of green spaces, with more than 50 percent of the city covered by forest
        // and parkland. Oslo is a city of contrasts, with a rich cultural life and a
        // vibrant nightlife. Oslo is also a city of green spaces, with more than 50
        // percent of the city covered by forest and parkland. Oslo is a city of
        // contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a
        // city of green spaces, with more than 50 percent of the city covered by forest
        // and parkland. Oslo is a city of contrasts, with a rich cultural life and a
        // vibrant nightlife. Oslo is also a city of green spaces, with more than 50
        // percent of the city covered by forest");
        // t.saveDestination("Bergen", "Norway", "Bergen is a city and municipality in
        // Hordaland on the west coast of Norway. Bergen is the second-largest city in
        // Norway, the municipality covers 465 square kilometres (180 sq mi) and is home
        // to 278,121 inhabitants. Bergen is the administrative centre of Hordaland and
        // consists of eight boroughs: Arna, Bergenhus, Fana, Fyllingsdalen, Laksevåg,
        // Ytrebygda, Årstad and Åsane. The city centre and northern neighbourhoods are
        // on Byfjorden, the city's southern neighbourhoods are on the island of
        // Bergenøya, and the western neighbourhoods are on the peninsula of
        // Bergenshalvøyen. The city is an international centre for aquaculture,
        // shipping, offshore petroleum industry and subsea technology, and a national
        // centre for higher education, media, tourism and finance. Bergen Port is the
        // busiest in Norway and one of the largest in Northern Europe. The city is an
        // important centre for industries and maritime trade in Europe. ");
        // t.saveDestination("Trondheim", "Norway", "Trondheim is a city and
        // municipality in Sør-Trøndelag county, Norway. It has a population of 187,353
        // (as of 1 January 2018) and is the third most populous municipality in Norway.
        // The city functions as the administrative centre of Sør-Trøndelag county. The
        // municipality is the third largest by population in Norway and the fourth
        // largest municipal area. Trondheim lies on the south shore of the Trondheim
        // Fjord at the mouth of the river Nidelva. The settlement was founded in 997 as
        // a trading post and became the capital of Norway in 1130. The city was named
        // Strindheim in the early 12th century after the farm Strinda, which was the
        // residence of the bishop. The name Trondheim was used first in 1217 and is
        // derived from the Old Norse words trönd and heimr. The city's name therefore
        // translates to crossing place(s) of the fjord(s).");
        // t.saveDestination("Stavanger", "Norway", "Stavanger is a city and
        // municipality in Norway. It is the fourth-largest city in the country, with a
        // population of 122,388 as of 1 January 2018. The municipality is the third
        // largest by population in Norway and the fourth largest municipal area. The
        // city is the administrative centre of Rogaland county. Stavanger is the centre
        // of the Stavanger metropolitan region, which has a population of 279,000. The
        // city is an international centre for the oil industry and shipping, and a
        // national centre for aquaculture, higher education, tourism and finance.
        // Stavanger is the administrative centre of Rogaland county. The city is the
        // centre of the Stavanger metropolitan region, which has a population of
        // 279,000. The city is an international centre for the oil industry and
        // shipping, and a national centre for aquaculture, higher education, tourism
        // and finance. Stavanger is the administrative centre of Rogaland county. The
        // city is the centre of the Stavanger metropolitan region, which has a
        // population of 279,000. The city is an international centre for the oil
        // industry and shipping, and a national centre for aquaculture, higher
        // education, tourism and finance. Stavanger is the administrative centre of
        // Rogaland county. The city is the centre of the Stavanger metropolitan region,
        // which has a population of 279,000. The city is an international centre for
        // the oil industry and shipping, and a national centre for aquaculture, higher
        // education, tourism and finance. Stavanger is the administrative centre of
        // Rogaland county. The city is the centre of the Stavanger metropolitan region,
        // which has a population of 279,000. The city is an international centre for
        // the oil industry and shipping, and a national centre for aquaculture, higher
        // education, tourism and finance. Stavanger is the administrative centre of
        // Rogaland county. The city is the centre of the Stavanger metropolitan region,
        // which has a population of 279,000. The city is an international centre for
        // the oil industry and shipping, and a national centre for aquaculture, higher
        // education, tourism and finance. Stavanger is the administrative centre of
        // Rogaland county. The city is the centre of the Stavanger metropolitan region,
        // which has a population of 279,000. The city is an international centre for
        // the oil industry and shipping, and a national");
        // t.saveDestination("Tromsø", "Norway", "Tromsø is a city and municipality in
        // Troms county, Norway. It is the most populous city in Northern Norway. The
        // administrative centre of Troms county, Tromsø lies on the northern coast of
        // the island of Tromsøya, about 350 kilometres (220 mi) north of the Arctic
        // Circle. The city is the largest settlement in the county, and the 11th most
        // populous urban area in Norway. Tromsø has a population of 69,515 (as of 1
        // January 2018), and is the fourth most populous urban area in the country. The
        // city is a major centre for maritime industries and shipping, and is the
        // location of the Arctic University of Norway. Tromsø is also a popular tourist
        // destination, and is known for its northern lights, midnight sun, and the
        // annual Tromsø International Film Festival. Tromsø is a city and municipality
        // in Troms county, Norway. It is the most populous city in Northern Norway. The
        // administrative centre of Troms county, Tromsø lies on the northern coast of
        // the island of Tromsøya, about 350 kilometres (220 mi) north of the Arctic
        // Circle. The city is the largest settlement in the county, and the 11th most
        // populous urban area in Norway. Tromsø has a population of 69,515 (as of 1
        // January 2018), and is the fourth most populous urban area in the country. The
        // city is a major centre for maritime industries and shipping, and is the
        // location of the Arctic University of Norway. Tromsø is also a popular tourist
        // destination, and is known for its northern lights, midnight sun, and the
        // annual Tromsø International Film Festival. Tromsø is a city and municipality
        // in Troms county, Norway. It is the most populous city in Northern Norway. The
        // administrative centre of Troms county, Tromsø lies on the northern coast of
        // the island of Tromsøya, about 350 kilometres (220 mi) north of the Arctic
        // Circle. The city is the largest settlement in the county, and the 11th most
        // populous urban area in Norway. Tromsø has a population of 69,515 (as of 1
        // January 2018), and is the fourth most populous urban area in the country. The
        // city is a major centre for maritime industries and shipping, and is the
        // location of the Arctic University of Norway.");
        // t.saveDestination("spiterstulen", "Norway", "Spiterstulen is located 1111
        // meters above sea level in the lush Visdalen in Jotunheimen, and is a natural
        // starting point for trips to Norway's highest mountain - Galdhøpiggen. Here
        // you can also reach 17 of the 26 peaks in Norway at over 2,300 meters above
        // sea level on a day trip. Big brother Galdhøpiggen reigns in the west with
        // little brother Glittertind in the east. When the cabin opens in the spring,
        // the area is an eldorado for top ski tours.");
        // t.saveDestination("Gjendebu", "Norway", "Gjendebu is a mountain cabin in the
        // Jotunheimen National Park, Norway. It is located at 1,200 metres (3,900 ft)
        // above sea level, on the south side of the Gjende lake, in the Gjende valley.
        // The cabin is open from May to October. The cabin is located at the end of the
        // Gjendebu road, which is open to cars from the end of May to the end of
        // September. The cabin is located at the end of the Gjendebu road, which is
        // open to cars from the end of May to the end of September. The cabin is
        // located at the end of the Gjendebu road, which is open to cars from the end
        // of May to the end of September. The cabin is located at the end of the
        // Gjendebu road, which is open to cars from the end of May to the end of
        // September. The cabin is located at the end of the Gjendebu road, which is
        // open to cars from the end of May to the end of September. The cabin is
        // located at the end of the Gjendebu road, which is open to cars from the end
        // of May to the end of September. The cabin is located at the end of the
        // Gjendebu road, which is open to cars from the end of May to the end of
        // September. The cabin is located at the end of the Gjendebu road, which is
        // open to cars from the end of May to the end of September. The cabin is
        // located at the end of the Gjendebu road, which is open to cars from the end
        // of May to the end of September. The cabin is located at the end of the
        // Gjendebu road, which is open to cars from the end of May to the end of
        // September. The cabin is located at the end of the Gjendebu road, which is
        // open to cars from the end of May to the end of September. The cabin is
        // located at the end of the Gjendebu road, which is open to cars from the end
        // of May to the end of September. The cabin is located at the end of the
        // Gjendebu road, which is open to cars from the end of May to the end of
        // September. The cabin is located at the end of the Gjendebu road,");
        // t.saveDestination("Finse", "Norway", "Finse is a mountain village area on the
        // shore of the lake Finsevatnet in Ulvik municipality in Vestland county,
        // Norway. The village is centered on Finse Station, a railway station on the
        // Bergen Line. The village sits at an elevation of 1,222 metres (4,009 ft)
        // above sea level, making it the highest station on the entire Norwegian
        // railway system. ");
        // t.saveDestination("Mount Everest", "Nepal", "Mount Everest, also known in
        // Nepal as Sagarmatha and in China as Chomolungma/珠穆朗玛峰, is Earth's highest
        // mountain above sea level, located in the Mahalangur Himal sub-range of the
        // Himalayas. The China–Nepal border runs across its summit point. Mount Everest
        // is 8,848 metres (29,029 ft) tall, which is 2.23 metres (7.3 ft) less than the
        // previously accepted height of 8,850 metres (29,035 ft). The international
        // community recognizes the height of 8,848 metres (29,029 ft) as the official
        // height of Mount Everest. The current official height of 8,848 metres (29,029
        // ft) was established by a 1955 Indian survey and confirmed by a 1975 Chinese
        // survey. The height was most recently revised in 1999 by a Nepalese survey,
        // which found that the mountain was 2.23 metres (7.3 ft) shorter than the
        // previous height. The mountain was named after Sir George Everest, who was the
        // Surveyor General of India from 1830 to 1843. The first recorded ascent of
        // Everest was in 1953 by New Zealand mountaineer Edmund Hillary and Sherpa
        // Tenzing Norgay. ");
        // t.saveDestination("Kilimanjaro", "Tanzania", "Kilimanjaro, also known as
        // Kibo, is a dormant volcano in Tanzania. It is the highest mountain in Africa,
        // and rises approximately 4,900 metres (16,000 ft) from its base to 5,895
        // metres (19,341 ft) above sea level. It is the highest point in both Tanzania
        // and Kenya. The first recorded ascent of Kilimanjaro was in 1889 by Hans Meyer
        // and Ludwig Purtscheller. The mountain is a popular climbing destination, with
        // more than 45,000 people attempting to reach the summit each year. ");
        // t.saveDestination("Aconcagua", "Argentina", "Aconcagua is the highest
        // mountain in the Americas and the highest mountain outside Asia. It is located
        // in the Mendoza Province, in the Argentinean Andes, about 80 kilometres (50
        // mi) northwest of the provincial capital, Mendoza. The mountain lies on the
        // border between Argentina and Chile. The mountain is named after Aconcagua, a
        // mythical giant in the Andean mythology. The first ascent of Aconcagua was
        // made by a Swiss expedition led by Edward Whymper in 1897. ");
        // t.saveDestination("Denali", "United States", "Denali is the highest mountain
        // peak in North America, with a summit elevation of 20,310 feet (6,190 m) above
        // sea level. Denali is located in the Denali National Park and Preserve in
        // Alaska, United States, about 150 miles (240 km) south of the Arctic Circle.
        // The mountain was formerly known as Mount McKinley, but its native name was
        // restored by presidential proclamation in 2015. ");
        // t.saveDestination("Vesuvius", "Italy", "Mount Vesuvius is a
        // somma-stratovolcano located on the Gulf of Naples in Campania, Italy, about 9
        // km (5.6 mi) east of Naples and a short distance from the shore. It is one of
        // several volcanoes which form the Campanian volcanic arc. Vesuvius consists of
        // a large cone partially encircled by the steep rim of a summit caldera caused
        // by the collapse of an earlier and originally much higher structure. Vesuvius
        // is best known for its eruption in AD 79 that led to the burying and
        // destruction of the Roman cities of Pompeii and Herculaneum. ");
        // t.saveDestination("Sisily", "Italy", "Mount Etna is an active stratovolcano
        // on the east coast of Sicily, Italy, in the Metropolitan City of Catania,
        // between the cities of Catania and Messina. It is the tallest active volcano
        // in Europe, currently 3,329 m (10,922 ft) high, though this varies with summit
        // eruptions. It is the highest peak in Italy south of the Alps. ");
        // t.saveDestination("Paris", "France", "Paris is the capital and most populous
        // city of France. It is situated on the Seine River, in the north of the
        // country, at the heart of the Île-de-France region. It is the centre of the
        // Paris metropolitan area, which has an estimated population of 12.2 million,
        // or 18.4 million if including the densely populated Île-de-France region
        // around the city. ");
        // t.saveDestination("London", "United Kingdom", "London is the capital and
        // largest city of England and the United Kingdom. Standing on the River Thames
        // in the south-east of England, at the head of its 50-mile (80 km) estuary
        // leading to the North Sea, London has been a major settlement for two
        // millennia. Londinium was founded by the Romans. ");
        // t.saveDestination("New York", "United States", "New York is the most populous
        // city in the United States. With an estimated 2019 population of 8,336,817
        // distributed over a land area of about 302.6 square miles (784 km2), New York
        // is also the most densely populated major city in the United States. Located
        // at the southern tip of the state of New York, the city is the center of the
        // New York metropolitan area, the largest metropolitan area in the world by
        // urban landmass and one of the world's most populous megacities, with an
        // estimated 2019 population of 20,321,000. ");
        // t.saveDestination("Tokyo", "Japan", "Tokyo is the capital of Japan, the
        // center of the Greater Tokyo Area, and the most populous metropolitan area in
        // the world. It is the seat of the Japanese government and the Imperial Palace,
        // and the home of the Japanese Imperial Family. Tokyo is in the Kantō region on
        // the southeastern side of the main island Honshu and includes the Izu Islands
        // and Ogasawara Islands. Tokyo Metropolis was formed in 1943 from the merger of
        // the former Tokyo Prefecture (東京府, Tōkyō-fu) and the city of Tokyo (東京市,
        // Tōkyō-shi). Tokyo Metropolis was dissolved on March 31, 1947, and Tokyo was
        // reestablished as a prefecture. Tokyo is often referred to as a city, but is
        // officially known and governed as a 'metropolitan prefecture', which differs
        // from and combines elements of a city and a prefecture, a characteristic
        // unique to Tokyo. ");
        // t.saveDestination("Sydney", "Australia", "Sydney is the state capital of New
        // South Wales and the most populous city in Australia and Oceania. Located on
        // Australia's east coast, the metropolis surrounds Port Jackson and extends
        // about 70 km (43.5 mi) on its periphery towards the Blue Mountains to the
        // west, Hawkesbury to the north, the Royal National Park to the south and
        // Macarthur to the south-west. Sydney is made up of 658 suburbs, 40 local
        // government areas and 15 contiguous regions. Residents of the city are known
        // as 'Sydneysiders'. As of June 2019, Sydney's estimated metropolitan
        // population was 5,230,330 and is home to approximately 65% of the state's
        // population. ");
        // t.saveDestination("Rio de Janeiro", "Brazil", "Rio de Janeiro, or simply Rio,
        // is the second-most populous municipality in Brazil and the sixth-most
        // populous in the Americas. The metropolis is anchor to the Rio de Janeiro
        // metropolitan area, the second-most populous metropolitan area in Brazil and
        // sixth-most populous in the Americas. Rio de Janeiro is the capital of the
        // state of Rio de Janeiro, Brazil's third-most populous state. Part of the city
        // has been designated as a World Heritage Site, named 'Rio de Janeiro: Carioca
        // Landscapes between the Mountain and the Sea', by UNESCO on 1 July 2012 as a
        // Cultural Landscape. ");
        // t.saveDestination("Cairo", "Egypt", "Cairo is the capital of Egypt. It is the
        // largest city in the Arab world, the largest city in Africa, and the Middle
        // East, and the 15th-largest city in the world. The city's metropolitan area is
        // the 16th-largest in the world and is associated with ancient Egypt, as the
        // famous Giza pyramid complex and the ancient city of Memphis are located in
        // its geographical area. ");
        // t.saveDestination("Moscow", "Russia", "Moscow is the capital and most
        // populous city of Russia, with 12.4 million residents within the city limits,
        // 17.1 million within the urban area, and 20.5 million within the metropolitan
        // area. The city stands on the Moskva River in the Central Federal District of
        // European Russia, making it Europe's most populated inland city. The city's
        // history spans more than 850 years, and it has played a significant role in
        // the development of Russia and the world. Moscow is the northernmost and
        // coldest megacity and metropolis on Earth. ");
        // t.saveDestination("Beijing", "China", "Beijing is the capital of the People's
        // Republic of China and one of the most populous cities in the world. The
        // population as of 2019 was 21,516,000. The metropolis, located in northern
        // China, is governed as a direct-controlled municipality under the national
        // government with 16 urban, suburban, and rural districts. Beijing Municipality
        // is surrounded by Hebei Province with the exception of neighboring Tianjin
        // Municipality to the southeast. ");
        // t.saveDestination("Shanghai", "China", "Shanghai is the most populous city
        // proper in the world, with a population of more than 24 million as of 2019. As
        // one of the four direct-controlled municipalities of the People's Republic of
        // China, it is a global city and financial centre with the second highest
        // number of skyscrapers in the world. It is also the world's busiest container
        // port and has the world's busiest airport by passenger traffic. ");
        // t.saveDestination("Seoul", "South Korea", "Seoul is the capital and largest
        // metropolis of South Korea. A megacity with a population of over 10 million,
        // it is the world's 4th largest metropolitan area and the world's 5th largest
        // city proper. The Seoul Capital Area, which includes the surrounding Incheon
        // metropolis and Gyeonggi province, is the world's 5th largest metropolitan
        // area with over 25 million people. ");
        // t.saveDestination("Bangkok", "Thailand", "Bangkok is the capital and most
        // populous city of Thailand. It is known in Thai as Krung Thep Maha Nakhon or
        // simply Krung Thep. The city occupies 1,568.7 square kilometres (605.7 sq mi)
        // in the Chao Phraya River delta in Central Thailand, and has a population of
        // over eight million, or 12.6 percent of the country's population. Over
        // fourteen million people (22.2 percent) live within the surrounding Bangkok
        // Metropolitan Region, making Bangkok an extreme primate city, significantly
        // dwarfing Thailand's other urban centres in terms of importance. ");
        // t.saveDestination("Hong Kong", "Hong Kong", "Hong Kong, officially the Hong
        // Kong Special Administrative Region of the People's Republic of China, is an
        // autonomous territory on the eastern side of the Pearl River estuary in East
        // Asia. With over 7.4 million people of various nationalities in a
        // 1,104-square-kilometre (426 sq mi) territory, Hong Kong is the world's fourth
        // most densely populated area. It is the most populous city in China, with 7.27
        // million people in a 1,104-square-kilometre (426 sq mi) territory. Hong Kong
        // is a Special Administrative Region (SAR) of the People's Republic of China.
        // ");
        // t.saveDestination("Singapore", "Singapore", "Singapore, officially the
        // Republic of Singapore, is a sovereign city-state and island country in
        // Southeast Asia. It lies one degree of latitude (137 kilometres or 85 miles)
        // north of the equator, at the southern tip of the Malay Peninsula, with
        // Indonesia's Riau Islands to the south and Peninsular Malaysia to the north.
        // Singapore's territory consists of one main island along with 62 other islets.
        // Since independence, extensive land reclamation has increased its total size
        // by 23% (130 km2 or 50 sq mi). ");
        // t.saveDestination("Taipei", "Taiwan", "Taipei, officially known as Taipei
        // City, is the capital and a special municipality of Taiwan (officially the
        // Republic of China). It is the political, economic, educational, and cultural
        // center of Taiwan. The Taipei City Government is currently the largest city
        // government in the world, with a total population of 2.98 million. Taipei is a
        // major hub of transportation in the region, and is home to the Taipei Songshan
        // Airport, Taiwan Taoyuan International Airport, and Taipei Port. ");

    }

}
