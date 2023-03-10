package group61.backpacking;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;

@Repository
public class ItineraryRepository {


    ///////////////////////////////////////////////////////////////////////////
    // helper functions


    public static Connection connectToDB() {
        Connection conn = null;
        String url = "jdbc:sqlite:bpDatabase.db";
        

        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite database established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    // not really helpful yet
    public Calendar getDate(){
        return Calendar.getInstance();
    }


    public String formatInput(String input) {
        String formattedInput = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
        return formattedInput;
    }

    ///////////////////////////////////////////////////////////////////////////
    // ITINERARY STUFF
    ///////////////////////////////////////////////////////////////////////////




    ///////////////////////////////////////////////////////////////////////////
    // save functions


    public void saveItineraryAndDestinations(Itinerary itinerary, Destination destination, Integer order) throws SQLException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            saveDestination(destination.getDestinationName(), destination.getCountry(), destination.getDescription());
        } catch (Exception e) {
            // Do nothing
        }

        try {

            conn = connectToDB();
            String sqlQuery = "INSERT INTO itinerary_destination (itinerary_id, destination_name, order_number, country) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            // db.update(preparedStatement, user.getUserName(), user.getPassword(),
            // user.getEmail());
            preparedStatement.setInt(1, itinerary.getId());
            preparedStatement.setString(2, destination.getDestinationName());
            preparedStatement.setInt(3, order);
            preparedStatement.setString(4, destination.getCountry());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);

            // throw new DuplicateUserException("User with email " + user.getEmail() + "
            // already exists");
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

    }


    public void saveItinerary(ItineraryAndDestinations itineraryAndDestinations) throws SQLException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Itinerary itinerary = itineraryAndDestinations.getItinerary();

        try {
            if (validateItinerary(itinerary) == false) {
                throw new SQLException("Itinerary with title " + itinerary.getTitle() + " already exists");
            }

            conn = connectToDB();
            String sqlQuery = "INSERT INTO Itinerary ( writer_email, estimated_time, itinerary_description, image, title, cost ) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            // db.update(preparedStatement, user.getUserName(), user.getPassword(),
            // user.getEmail());
            preparedStatement.setString(1, itinerary.getWriterEmail());
            preparedStatement.setInt(2, itinerary.getEstimatedTime());
            preparedStatement.setString(3, itinerary.getDescription());
            preparedStatement.setString(4, itinerary.getImage());
            preparedStatement.setString(5, itinerary.getTitle());
            preparedStatement.setDouble(6, itinerary.getCost());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
            // throw new DuplicateUserException("User with email " + user.getEmail() + "
            // already exists");
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

        Itinerary itineraryOutput = new Itinerary(-1, null, null, 0, null, null, null,0);

        try {
            itineraryOutput = loadItineraryByInput(itinerary.getTitle(), itinerary.getWriterEmail());
        } catch (Exception e) {
            // do nothing
        }
        System.out.println(itineraryOutput.toString());

        try {
            int totalDestinations = itineraryAndDestinations.getDestinations().size();

            for (int i = 0; i < totalDestinations; i++) {
                saveItineraryAndDestinations(itineraryOutput, itineraryAndDestinations.getDestinations().get(i), i+1);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

    }


    


    ///////////////////////////////////////////////////////////////////////////
    // validate functions

    public boolean validateItinerary(Itinerary inputItinerary) throws RuntimeException, SQLException {

        String title = inputItinerary.getTitle();
        String email = inputItinerary.getWriterEmail();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Itinerary itinerary = new Itinerary(-1, null,null,-1,null,null,null,0);

        try  {
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
        if(itinerary.getId() == -1){
            return true;
        }
        return false;
    }

    public boolean validateDestination(Destination destination) throws RuntimeException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Destination destination2 = new Destination(null, null, null);
        try {
            conn = connectToDB();
            String sqlQuery = "SELECT * FROM destination WHERE destination_name = ? AND country = ?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, destination.getDestinationName());
            statement.setString(2, destination.getCountry());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                destination2.mapDestinationFromResultSet(resultSet);
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
        if (destination2.getDestinationName() == null) {
            return true;
        }
        return false;
    }


    ///////////////////////////////////////////////////////////////////////////
    // load functions

    public Itinerary loadItineraryById(int id) throws RuntimeException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Itinerary itinerary = new Itinerary(-1, null,null,-1,null,null,null,0);

        try  {
            conn = connectToDB();
            String sqlQuery = "SELECT * FROM Itinerary WHERE id = ?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            
            resultSet = statement.executeQuery();

            
            while (resultSet.next()) {

                itinerary.mapItineraryFromResultSet(resultSet);
                
            }
            
        } catch (SQLException e) {
            System.out.println("Error in loadItinerary   1");
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

        if(itinerary.getId() == -1){
            return null;
        }

        
        return itinerary;
    }

    public Itinerary loadItineraryByInput(String title, String email) throws RuntimeException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Itinerary itinerary = new Itinerary(-1, null,null,-1,null,null,null,0);

        try  {
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

        if(itinerary.getId() == -1){
            return null;
        }

        
        return itinerary;
    }



    public List<Itinerary> loadItinerariesByUserEmail(String userEmail) throws RuntimeException, SQLException{
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
                Itinerary itinerary = new Itinerary(-1, null, null, 0, null, null, null,0);  // TODO: kostruktør i Itinerary
                itinerary.mapItineraryFromResultSet(resultSet);
                // TODO: Legge til destinations
                itineraries.add(itinerary);
            }

        } catch (Exception e) {
            // TODO: handle exception
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
        return itineraries;
    }

    

    

    

    public List<Itinerary> loadEveryItinerary() throws SQLException{

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Itinerary> itineraryList = new ArrayList<Itinerary>();

        try  {
            conn = connectToDB();
            String sqlQuery = "SELECT * FROM Itinerary";
            statement = conn.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery();
            

            while (resultSet.next()) {
                Itinerary itinerary = new Itinerary(0, null, null, 0, null, null, null,0);
                itinerary.mapItineraryFromResultSet(resultSet);
                itineraryList.add(itinerary);
            }

            
        } catch (SQLException e) {
            System.out.println("Error in loadItinerary   1");
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

        return itineraryList;
    }


    public List<Destination> loadDestinationsOnItinerary(Itinerary itinerary) throws SQLException{
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Destination> destinationList = new ArrayList<Destination>();

        try  {
            conn = connectToDB();
            String sqlQuery = "SELECT Itinerary_destination.destination_name, Destinations.country, Destinations.destination_description " +
            "FROM Itinerary_destination "+
            "JOIN Destinations "+
            "ON Itinerary_destination.destination_name = Destinations.destination_name "+
            "WHERE Itinerary_destination.itinerary_id = ? "+
            "ORDER BY Itinerary_destination.order_number ASC;";
            statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, itinerary.getId());
            resultSet = statement.executeQuery();
        

            while (resultSet.next()) {
    
                Destination destination = new Destination(null, null, null);
                destination.mapDestinationFromResultSet(resultSet);
                destinationList.add(destination);
            }
            
            
        } catch (SQLException e) {
            System.out.println("Error in loadItinerary   1");
            throw new SQLException(e);
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
        System.out.println(destinationList.size());
        return destinationList;


    }

    public List<ItineraryAndDestinations>loadItineraryAndDestinations(List<Itinerary> itineraryList) throws SQLException{
        List<ItineraryAndDestinations> itinerary_destinationList = new ArrayList<ItineraryAndDestinations>();
         
        
        for (Itinerary itinerary : itineraryList) {
            List<Destination> destinationList = new ArrayList<Destination>();
            destinationList = loadDestinationsOnItinerary(itinerary);
            ItineraryAndDestinations itinerary_destination = new ItineraryAndDestinations(itinerary, destinationList);
            itinerary_destinationList.add(itinerary_destination);
        }
        return itinerary_destinationList;
    }


    public List<Itinerary> searchByKeyword(String keyword) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        PreparedStatement statement2 = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        List<Itinerary> itineraryList = new ArrayList<Itinerary>();

        try  {
            conn = connectToDB();
            String sqlQuery = "SELECT * FROM Itinerary WHERE "
            + "title LIKE '%' || :keyword || '%' "
            + "OR description LIKE '%' || :keyword || '%'";
            statement = conn.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Itinerary itinerary = new Itinerary(0, null, null, 0, null, null, null, 0);
                itinerary.mapItineraryFromResultSet(resultSet);
                itineraryList.add(itinerary);
            }
            
            String sqlQuery2 = "SELECT * FROM Itinerary INNER JOIN Itinerary_destination ON (id = itinerary_id) "
            + "WHERE destination_name LIKE '%' || :keyword || '%' "
            + "OR country LIKE '%' || :keyword || '%'";
            statement2 = conn.prepareStatement(sqlQuery2);
            resultSet2 = statement.executeQuery();
            
            while (resultSet2.next()) {
                Itinerary itinerary = new Itinerary(0, null, null, 0, null, null, null, 0);
                itinerary.mapItineraryFromResultSet(resultSet2);
                itineraryList.add(itinerary);
            }

        } catch (SQLException exception) {
            throw new SQLException(exception);
        }    
        finally {
            if (statement != null) {
                statement.close();
            }
            if (statement2 != null) {
                statement2.close();
            }
            if (resultSet2 != null) {
                resultSet2.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return itineraryList;
    }


    


    



    
    ///////////////////////////////////////////////////////////////////////////
    // update / delete functions

    // sletting basert på tittel og email
    public void deleteItinerary_byEmail(String email, String title) throws SQLException{
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            
            conn = connectToDB();
            String sqlQuery = "DELETE FROM Itinerary WHERE title = ? AND writer_email = ?";
            preparedStatement = conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, email);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
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

    public void deleteItinerary(Itinerary itinerary) throws RuntimeException, SQLException{
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

        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////
    // Liked Itineraries

    public void updateLikedItinerary(String email, int itineraryId) throws SQLException {
        if (likedItinerary(email, itineraryId)) {
            deleteLikedItinerary(email, itineraryId);
        } else {
            saveLikedItinerary(email, itineraryId);
        }
    }

    private void deleteLikedItinerary(String email, int itineraryId) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = connectToDB();
            String sqlQuery = "DELETE FROM Liked_Itineraries WHERE user_email = ? AND itinerary_id = ?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, email);
            statement.setInt(2, itineraryId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
        }

        finally {
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    private void saveLikedItinerary(String email, int itineraryId) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = connectToDB();
            String sqlQuery = "INSERT INTO Liked_Itineraries (user_email, itinerary_id) VALUES (?, ?)";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, email);
            statement.setInt(2, itineraryId);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
        } 
        finally {
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public boolean likedItinerary(String email, int itineraryId) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean result = false;

        try {
            conn = connectToDB();
            String sqlQuery = "SELECT * FROM Liked_Itineraries WHERE "
            + "user_email = ? AND itinerary_id = ?";

            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, email);
            statement.setInt(2, itineraryId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                result = true;
            }

        } catch (SQLException e) {
            throw new SQLException(e);
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
        return result;
    }

    public List<Itinerary> loadLikedItineraries(String email) throws SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Itinerary> itineraryList = new ArrayList<Itinerary>();

        try  {
            conn = connectToDB();
            String sqlQuery = "SELECT * FROM Itinerary INNER JOIN Liked_Itineraries ON (id = itinerary_id) WHERE user_email = ?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Itinerary itinerary = new Itinerary(0, null, null, 0, null, null, null,0);
                itinerary.mapItineraryFromResultSet(resultSet);
                itineraryList.add(itinerary);
            }
            
        } catch (SQLException e) {
            throw new SQLException(e);
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

        return itineraryList;
    }

    



    ///////////////////////////////////////////////////////////////////////////
    // not completely related to Itinerary

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

    }

  /*   public void saveItineries() throws SQLException {s
        // Itinerary 1
        Itinerary itinerary1 = new Itinerary(10, "jarl@test.org", "", (int)(Math.random() * 41) + 10, "Chile is a backpacker's paradise, with stunning natural wonders and a rich culture to explore. On this trip, you'll experience the best of what Chile has to offer, from the bustling city of Santiago to the rugged landscapes of Patagonia. Hike through the Torres del Paine National Park, visit the Atacama Desert, and sample delicious Chilean cuisine along the way. This is an adventure you won't soon forget!", "", "Chile Backpacking Adventure", (int)(Math.random() * 2301) + 900);
        Destination destinationforitinerary1_1 = new Destination("Torres del Paine National Park", "Chile", "");
        Destination destinationforitinerary1_2 = new Destination("Atacama Desert", "Chile", "");
        Destination destinationforitinerary1_3 = new Destination("Easter Island", "Chile", "");
        List<Destination> destinationsOfItinerary1 = new ArrayList<>();
        destinationsOfItinerary1.add(destinationforitinerary1_1);
        destinationsOfItinerary1.add(destinationforitinerary1_2);
        destinationsOfItinerary1.add(destinationforitinerary1_3);
        ItineraryAndDestinations itinerary1AndDestinations = new ItineraryAndDestinations(itinerary1, destinationsOfItinerary1);
        saveItinerary(itinerary1AndDestinations);

        // Itinerary 2
        Itinerary itinerary2 = new Itinerary(11, "marisa@test.org", "", (int)(Math.random() * 41) + 10, "Experience the beauty and diversity of Europe on this unforgettable train journey. From the romance of Paris to the canals of Venice, you'll see it all. Explore the history of Berlin, take in the stunning Swiss Alps, and enjoy the vibrant nightlife of Amsterdam. This is the ultimate European adventure!", "", "Europe by Train" , (int)(Math.random() * 2301) + 900);
        Destination destinationforitinerary2_1 = new Destination("Paris", "France", "");
        Destination destinationforitinerary2_2 = new Destination("Berlin", "Germany", "");
        Destination destinationforitinerary2_3 = new Destination("Venice", "Italy", "");
        Destination destinationforitinerary2_4 = new Destination("Swiss Alps", "Switzerland", "");
        Destination destinationforitinerary2_5 = new Destination("Amsterdam", "Netherlands", "");
        List<Destination> destinationsOfItinerary2 = new ArrayList<>();
        destinationsOfItinerary2.add(destinationforitinerary2_1);
        destinationsOfItinerary2.add(destinationforitinerary2_2);
        destinationsOfItinerary2.add(destinationforitinerary2_3);
        destinationsOfItinerary2.add(destinationforitinerary2_4);
        destinationsOfItinerary2.add(destinationforitinerary2_5);
        ItineraryAndDestinations itinerary2AndDestinations = new ItineraryAndDestinations(itinerary2, destinationsOfItinerary2);
        saveItinerary(itinerary2AndDestinations);

        // Itinerary 3
        Itinerary itinerary3 = new Itinerary(12, "tobias@test.org", "", (int)(Math.random() * 41) + 10, "Experience the rich and diverse cultures of Asia on this incredible tour. Visit ancient temples, bustling markets, and stunning natural landscapes. Explore the vibrant cities of Tokyo and Seoul, and discover the hidden gems of Vietnam and Cambodia. This is a journey you'll never forget!", "", "Discovering Asia", (int)(Math.random() * 2301) + 900);
        Destination destinationforitinerary3_1 = new Destination("Angkor Wat", "Cambodia", "");
        Destination destinationforitinerary3_2 = new Destination("Halong Bay", "Vietnam", "");
        Destination destinationforitinerary3_3 = new Destination("Tokyo", "Japan", "");
        Destination destinationforitinerary3_4 = new Destination("Seoul", "South Korea", "");
        List<Destination> destinationsOfItinerary3 = new ArrayList<>();
        destinationsOfItinerary3.add(destinationforitinerary3_1);
        destinationsOfItinerary3.add(destinationforitinerary3_2);
        destinationsOfItinerary3.add(destinationforitinerary3_3);
        destinationsOfItinerary3.add(destinationforitinerary3_4);
        ItineraryAndDestinations itinerary3AndDestinations = new ItineraryAndDestinations(itinerary3, destinationsOfItinerary3);
        saveItinerary(itinerary3AndDestinations);

        // Itinerary 4
        Itinerary itinerary4 = new Itinerary(13, "simen@test.org", "", (int)(Math.random() * 41) + 10, "Embark on an unforgettable journey through South America, where you'll experience the stunning natural beauty, rich culture, and amazing cuisine. Explore the Amazon rainforest, visit Machu Picchu, and discover the vibrant cities of Rio de Janeiro and Buenos Aires. This is an adventure of a lifetime!", "", "South America Expedition", (int)(Math.random() * 2301) + 900);
        Destination destinationforitinerary4_1 = new Destination("Machu Picchu", "Peru", "");
        Destination destinationforitinerary4_2 = new Destination("Amazon Rainforest", "Brazil", "");
        Destination destinationforitinerary4_3 = new Destination("Rio de Janeiro", "Brazil", "");
        Destination destinationforitinerary4_4 = new Destination("Buenos Aires", "Argentina", "");
        List<Destination> destinationsOfItinerary4 = new ArrayList<>();
        destinationsOfItinerary4.add(destinationforitinerary4_1);
        destinationsOfItinerary4.add(destinationforitinerary4_2);
        destinationsOfItinerary4.add(destinationforitinerary4_3);
        destinationsOfItinerary4.add(destinationforitinerary4_4);
        ItineraryAndDestinations itinerary4AndDestinations = new ItineraryAndDestinations(itinerary4, destinationsOfItinerary4);
        saveItinerary(itinerary4AndDestinations);

        // Itinerary 5
        Itinerary itinerary5 = new Itinerary(14, "emil@test.org", "", (int)(Math.random() * 41) + 10,"Experience the beauty of the American West on this unforgettable journey. From the rugged Rocky Mountains to the stunning deserts of Arizona, you'll discover the awe-inspiring landscapes that have inspired artists and adventurers for generations. Explore the vibrant cities of Denver and Phoenix, and discover the rich cultural heritage of the Native American tribes that call this land home.", "", "Wild West Adventure", (int)(Math.random() * 2301) + 900);
        Destination destinationforitinerary5_1 = new Destination("Grand Canyon", "USA", "");
        Destination destinationforitinerary5_2 = new Destination("Yellowstone National Park", "USA", "");
        Destination destinationforitinerary5_3 = new Destination("Denver", "USA", "");
        Destination destinationforitinerary5_4 = new Destination("Phoenix", "USA", "");
        List<Destination> destinationsOfItinerary5 = new ArrayList<>();
        destinationsOfItinerary5.add(destinationforitinerary5_1);
        destinationsOfItinerary5.add(destinationforitinerary5_2);
        destinationsOfItinerary5.add(destinationforitinerary5_3);
        destinationsOfItinerary5.add(destinationforitinerary5_4);
        ItineraryAndDestinations itinerary5AndDestinations = new ItineraryAndDestinations(itinerary5, destinationsOfItinerary5);
        saveItinerary(itinerary5AndDestinations);

        // Itinerary 6
        Itinerary itinerary6 = new Itinerary(15, "aurora@test.org", "", (int)(Math.random() * 41) + 10, "Discover the idyllic islands of the Mediterranean on this breathtaking adventure. From the ancient ruins of Greece to the stunning beaches of Croatia, you'll explore some of the most beautiful destinations in the world. Swim in crystal-clear waters, sample delicious local cuisine, and soak up the laid-back Mediterranean lifestyle. This is the ultimate island-hopping experience!", "", "Mediterranean Islands", (int)(Math.random() * 2301) + 900);
        Destination destinationforitinerary6_1 = new Destination("Santorini", "Greece", "");
        Destination destinationforitinerary6_2 = new Destination("Hvar", "Croatia", "");
        Destination destinationforitinerary6_3 = new Destination("Capri", "Italy", "");
        Destination destinationforitinerary6_4 = new Destination("Corsica", "France", "");
        List<Destination> destinationsOfItinerary6 = new ArrayList<>();
        destinationsOfItinerary6.add(destinationforitinerary6_1);
        destinationsOfItinerary6.add(destinationforitinerary6_2);
        destinationsOfItinerary6.add(destinationforitinerary6_3);
        destinationsOfItinerary6.add(destinationforitinerary6_4);
        ItineraryAndDestinations itinerary6AndDestinations = new ItineraryAndDestinations(itinerary6, destinationsOfItinerary6);
        saveItinerary(itinerary6AndDestinations);

        // Itinerary 7
        Itinerary itinerary7 = new Itinerary(16, "marisa@test.org", "", (int)(Math.random() * 41) + 10, "Hit the open road on this epic cross-country adventure. From the bright lights of New York City to the sun-soaked beaches of California, you'll explore the diverse landscapes and cultures that make America great. Visit iconic landmarks like the Grand Canyon and Mount Rushmore, and discover hidden gems off the beaten path. This is the ultimate American road trip!", "", "Cross-Country Odyssey", (int)(Math.random() * 2301) + 900);
        Destination destinationforitinerary7_1 = new Destination("New York City", "USA", "");
        Destination destinationforitinerary7_2 = new Destination("Grand Canyon", "USA", "");
        Destination destinationforitinerary7_3 = new Destination("Mount Rushmore", "USA", "");
        Destination destinationforitinerary7_4 = new Destination("Los Angeles", "USA", "");
        List<Destination> destinationsOfItinerary7 = new ArrayList<>();
        destinationsOfItinerary7.add(destinationforitinerary7_1);
        destinationsOfItinerary7.add(destinationforitinerary7_2);
        destinationsOfItinerary7.add(destinationforitinerary7_3);
        destinationsOfItinerary7.add(destinationforitinerary7_4);
        ItineraryAndDestinations itinerary7AndDestinations = new ItineraryAndDestinations(itinerary7, destinationsOfItinerary7);
        saveItinerary(itinerary7AndDestinations);

        // Itinerary 8
        Itinerary itinerary8 = new Itinerary(17, "simen@test.org", "", (int)(Math.random() * 41) + 10, "Embark on a journey to the top of the world on this unforgettable trek through the Himalayas. Hike through remote mountain villages, encounter ancient Buddhist monasteries, and take in stunning panoramic views of the world's highest peaks. You'll tackle challenging terrain and altitude, but the sense of accomplishment and awe-inspiring beauty will be worth it. This is the adventure of a lifetime!", "", "Himalayan Trek", (int)(Math.random() * 2301) + 900);
        Destination destinationforitinerary8_1 = new Destination("Everest Base Camp", "Nepal", "");
        Destination destinationforitinerary8_2 = new Destination("Annapurna Circuit", "Nepal", "");
        Destination destinationforitinerary8_3 = new Destination("Tibet", "China", "");
        Destination destinationforitinerary8_4 = new Destination("Kathmandu", "Nepal", "");
        List<Destination> destinationsOfItinerary8 = new ArrayList<>();
        destinationsOfItinerary8.add(destinationforitinerary8_1);
        destinationsOfItinerary8.add(destinationforitinerary8_2);
        destinationsOfItinerary8.add(destinationforitinerary8_3);
        destinationsOfItinerary8.add(destinationforitinerary8_4);
        ItineraryAndDestinations itinerary8AndDestinations = new ItineraryAndDestinations(itinerary8, destinationsOfItinerary8);
        saveItinerary(itinerary8AndDestinations);

        Itinerary itinerary9 = new Itinerary(18, "tobias@test.org", "", 12, "Experience the wonders of Peru on this backpacking adventure! From the breathtaking heights of Machu Picchu to the colorful streets of Cusco, this trip is sure to be a once-in-a-lifetime experience.", "", "The wonders of Peru", 2500);
        Destination destinationforitinerary9_1 = new Destination("Machu Picchu", "Peru", "");
        Destination destinationforitinerary9_2 = new Destination("Cusco", "Peru", "");
        Destination destinationforitinerary9_3 = new Destination("Lake Titicaca", "Peru", "");
        List<Destination> destinationsOfItinerary9 = new ArrayList<>();
        destinationsOfItinerary9.add(destinationforitinerary9_1);
        destinationsOfItinerary9.add(destinationforitinerary9_2);
        destinationsOfItinerary9.add(destinationforitinerary9_3);
        ItineraryAndDestinations itinerary9AndDestinations = new ItineraryAndDestinations(itinerary9, destinationsOfItinerary9);
        saveItinerary(itinerary9AndDestinations);

        Itinerary itinerary10 = new Itinerary(19, "marisa@test.org", "", 30, "All aboard! Get ready to see the stunning sights of Europe from the comfort of a train. From the romantic streets of Paris to the historic landmarks of Berlin, this trip is a must for any travel enthusiast.", "", "Europe by train", 2800);
        Destination destinationforitinerary10_1 = new Destination("Paris", "France", "");
        Destination destinationforitinerary10_2 = new Destination("Venice", "Italy", "");
        Destination destinationforitinerary10_3 = new Destination("Berlin", "Germany", "");
        Destination destinationforitinerary10_4 = new Destination("Vienna", "Austria", "");
        List<Destination> destinationsOfItinerary10 = new ArrayList<>();
        destinationsOfItinerary10.add(destinationforitinerary10_1);
        destinationsOfItinerary10.add(destinationforitinerary10_2);
        destinationsOfItinerary10.add(destinationforitinerary10_3);
        destinationsOfItinerary10.add(destinationforitinerary10_4);
        ItineraryAndDestinations itinerary10AndDestinations = new ItineraryAndDestinations(itinerary10, destinationsOfItinerary10);
        saveItinerary(itinerary10AndDestinations);

        Itinerary itinerary11 = new Itinerary(20, "aurora@test.org", "", 15, "Experience the beauty and culture of Japan on this train trip through some of the country's most iconic cities. From the neon lights of Tokyo to the serene temples of Kyoto, this trip is sure to be an unforgettable adventure.", "", "Experience beautiful Japan", 3100);
        Destination destinationforitinerary11_1 = new Destination("Tokyo", "Japan", "");
        Destination destinationforitinerary11_2 = new Destination("Kyoto", "Japan", "");
        Destination destinationforitinerary11_3 = new Destination("Hiroshima", "Japan", "");
        Destination destinationforitinerary11_4 = new Destination("Osaka", "Japan", "");
        List<Destination> destinationsOfItinerary11 = new ArrayList<>();
        destinationsOfItinerary11.add(destinationforitinerary11_1);
        destinationsOfItinerary11.add(destinationforitinerary11_2);
        destinationsOfItinerary11.add(destinationforitinerary11_3);
        destinationsOfItinerary11.add(destinationforitinerary11_4);
        ItineraryAndDestinations itinerary11AndDestinations = new ItineraryAndDestinations(itinerary11, destinationsOfItinerary11);
        saveItinerary(itinerary11AndDestinations);

        Itinerary itinerary12 = new Itinerary(21, "emil@test.org", "", 40, "Get ready to explore the wilds of Patagonia on this backpacking trip of a lifetime. From the soaring peaks of the Andes to the crystal clear lakes and rivers, this trip is sure to satisfy any adventurer's thirst for the great outdoors.", "", "Explore the wonders of Patagonia", 2000);
        Destination destinationforitinerary12_1 = new Destination("Torres del Paine National Park", "Chile", "");
        Destination destinationforitinerary12_2 = new Destination("El Chalten", "Argentina", "");
        Destination destinationforitinerary12_3 = new Destination("Perito Moreno Glacier", "Argentina", "");
        List<Destination> destinationsOfItinerary12 = new ArrayList<>();
        destinationsOfItinerary12.add(destinationforitinerary12_1);
        destinationsOfItinerary12.add(destinationforitinerary12_2);
        destinationsOfItinerary12.add(destinationforitinerary12_3);
        ItineraryAndDestinations itinerary12AndDestinations = new ItineraryAndDestinations(itinerary12, destinationsOfItinerary12);
        saveItinerary(itinerary12AndDestinations);

        Itinerary itinerary13 = new Itinerary(22, "tobias@test.org", "", 30, "Get ready to hit the open road on this ultimate European adventure. From the sunny beaches of Spain to the romantic canals of Venice, this trip has it all. Pack your bags, grab some snacks, and let's go!", "", "The Ultimate European Road Trip", 2800);
        Destination destinationforitinerary13_1 = new Destination("Barcelona", "Spain", "");
        Destination destinationforitinerary13_2 = new Destination("Nice", "France", "");
        Destination destinationforitinerary13_3 = new Destination("Cinque Terre", "Italy", "");
        Destination destinationforitinerary13_4 = new Destination("Venice", "Italy", "");
        Destination destinationforitinerary13_5 = new Destination("Vienna", "Austria", "");
        Destination destinationforitinerary13_6 = new Destination("Munich", "Germany", "");
        Destination destinationforitinerary13_7 = new Destination("Amsterdam", "Netherlands", "");
        List<Destination> destinationsOfItinerary13 = new ArrayList<>();
        destinationsOfItinerary13.add(destinationforitinerary13_1);
        destinationsOfItinerary13.add(destinationforitinerary13_2);
        destinationsOfItinerary13.add(destinationforitinerary13_3);
        destinationsOfItinerary13.add(destinationforitinerary13_4);
        destinationsOfItinerary13.add(destinationforitinerary13_5);
        destinationsOfItinerary13.add(destinationforitinerary13_6);
        destinationsOfItinerary13.add(destinationforitinerary13_7);
        ItineraryAndDestinations itinerary13AndDestinations = new ItineraryAndDestinations(itinerary13, destinationsOfItinerary13);
        saveItinerary(itinerary13AndDestinations);

        Itinerary itinerary14 = new Itinerary(23, "tobias@test.org", "", 35, "Embark on the adventure of a lifetime with this ultimate backpacking trip through Europe. From the art and architecture of Paris to the beaches of Barcelona, this trip has it all. Experience the best of what Europe has to offer on this unforgettable journey.", "", "Ultimate European Backpacking Adventure", 2800);
        Destination destinationforitinerary14_1 = new Destination("Paris", "France", "");
        Destination destinationforitinerary14_2 = new Destination("Berlin", "Germany", "");
        Destination destinationforitinerary14_3 = new Destination("Prague", "Czech Republic", "");
        Destination destinationforitinerary14_4 = new Destination("Vienna", "Austria", "");
        Destination destinationforitinerary14_5 = new Destination("Rome", "Italy", "");
        Destination destinationforitinerary14_6 = new Destination("Barcelona", "Spain", "");
        Destination destinationforitinerary14_7 = new Destination("Amsterdam", "Netherlands", "");
        List<Destination> destinationsOfItinerary14 = new ArrayList<>();
        destinationsOfItinerary14.add(destinationforitinerary14_1);
        destinationsOfItinerary14.add(destinationforitinerary14_2);
        destinationsOfItinerary14.add(destinationforitinerary14_3);
        destinationsOfItinerary14.add(destinationforitinerary14_4);
        destinationsOfItinerary14.add(destinationforitinerary14_5);
        destinationsOfItinerary14.add(destinationforitinerary14_6);
        destinationsOfItinerary14.add(destinationforitinerary14_7);
        ItineraryAndDestinations itinerary14AndDestinations = new ItineraryAndDestinations(itinerary14, destinationsOfItinerary14);
        saveItinerary(itinerary14AndDestinations);

        Itinerary itinerary15 = new Itinerary(24, "simen@test.org", "", 25, "Experience the vibrant culture, delicious cuisine, and stunning scenery of Southeast Asia on this epic adventure. From bustling cities to tranquil beaches, this trip has it all.", "", "Exploring Southeast Asia", 2800);
        Destination destinationforitinerary15_1 = new Destination("Bangkok", "Thailand", "");
        Destination destinationforitinerary15_2 = new Destination("Siem Reap", "Cambodia", "");
        Destination destinationforitinerary15_3 = new Destination("Hanoi", "Vietnam", "");
        Destination destinationforitinerary15_4 = new Destination("Luang Prabang", "Laos", "");
        Destination destinationforitinerary15_5 = new Destination("Yangon", "Myanmar", "");
        Destination destinationforitinerary15_6 = new Destination("Bali", "Indonesia", "");
        Destination destinationforitinerary15_7 = new Destination("Singapore", "Singapore", "");
        Destination destinationforitinerary15_8 = new Destination("Kuala Lumpur", "Malaysia", "");
        List<Destination> destinationsOfItinerary15 = new ArrayList<>();
        destinationsOfItinerary15.add(destinationforitinerary15_1);
        destinationsOfItinerary15.add(destinationforitinerary15_2);
        destinationsOfItinerary15.add(destinationforitinerary15_3);
        destinationsOfItinerary15.add(destinationforitinerary15_4);
        destinationsOfItinerary15.add(destinationforitinerary15_5);
        destinationsOfItinerary15.add(destinationforitinerary15_6);
        destinationsOfItinerary15.add(destinationforitinerary15_7);
        destinationsOfItinerary15.add(destinationforitinerary15_8);
        ItineraryAndDestinations itinerary15AndDestinations = new ItineraryAndDestinations(itinerary15, destinationsOfItinerary15);
        saveItinerary(itinerary15AndDestinations);

    } */


    public void saveInitialContintentsCountriesAndDestinations() throws SQLException{
        

        saveContinent("Europe");
        saveContinent("Asia");
        saveContinent("North America");
        saveContinent("South America");
        saveContinent("Africa");
        saveContinent("Oceania");
        saveContinent("Antarctica");

        saveCountry("Afghanistan", "Asia");
        saveCountry("Bahrain", "Asia");
        saveCountry("Bangladesh", "Asia");
        saveCountry("Bhutan", "Asia");
        saveCountry("Brunei", "Asia");
        saveCountry("Cambodia", "Asia");
        saveCountry("China", "Asia");
        saveCountry("Georgia", "Asia");
        saveCountry("India", "Asia");
        saveCountry("Indonesia", "Asia");
        saveCountry("Iran", "Asia");
        saveCountry("Iraq", "Asia");
        saveCountry("Israel", "Asia");
        saveCountry("Japan", "Asia");
        saveCountry("Jordan", "Asia");
        saveCountry("Kazakhstan", "Asia");
        saveCountry("Kuwait", "Asia");
        saveCountry("Kyrgyzstan", "Asia");
        saveCountry("Laos", "Asia");
        saveCountry("Lebanon", "Asia");
        saveCountry("Malaysia", "Asia");
        saveCountry("Maldives", "Asia");
        saveCountry("Mongolia", "Asia");
        saveCountry("Myanmar", "Asia");
        saveCountry("Nepal", "Asia");
        saveCountry("North Korea", "Asia");
        saveCountry("Oman", "Asia");
        saveCountry("Pakistan", "Asia");
        saveCountry("Palestine", "Asia");
        saveCountry("Philippines", "Asia");
        saveCountry("Qatar", "Asia");
        saveCountry("Saudi Arabia", "Asia");
        saveCountry("Singapore", "Asia");
        saveCountry("South Korea", "Asia");
        saveCountry("Sri Lanka", "Asia");
        saveCountry("Syria", "Asia");
        saveCountry("Taiwan", "Asia");
        saveCountry("Tajikistan", "Asia");
        saveCountry("Thailand", "Asia");
        saveCountry("Timor-Leste", "Asia");
        saveCountry("Turkey", "Asia");
        saveCountry("Turkmenistan", "Asia");
        saveCountry("United Arab Emirates", "Asia");
        saveCountry("Uzbekistan", "Asia");
        saveCountry("Vietnam", "Asia");
        saveCountry("Yemen", "Asia");
        saveCountry("Algeria", "Africa");
        saveCountry("Angola", "Africa");
        saveCountry("Benin", "Africa");
        saveCountry("Botswana", "Africa");
        saveCountry("Burkina Faso", "Africa");
        saveCountry("Burundi", "Africa");
        saveCountry("Cabo Verde", "Africa");
        saveCountry("Cameroon", "Africa");
        saveCountry("Central African Republic", "Africa");
        saveCountry("Chad", "Africa");
        saveCountry("Comoros", "Africa");
        saveCountry("Congo", "Africa");
        saveCountry("Cote d'Ivoire", "Africa");
        saveCountry("Djibouti", "Africa");
        saveCountry("Egypt", "Africa");
        saveCountry("Equatorial Guinea", "Africa");
        saveCountry("Eritrea", "Africa");
        saveCountry("Eswatini", "Africa");
        saveCountry("Ethiopia", "Africa");
        saveCountry("Gabon", "Africa");
        saveCountry("Gambia", "Africa");
        saveCountry("Ghana", "Africa");
        saveCountry("Guinea", "Africa");
        saveCountry("Guinea-Bissau", "Africa");
        saveCountry("Kenya", "Africa");
        saveCountry("Lesotho", "Africa");
        saveCountry("Liberia", "Africa");
        saveCountry("Libya", "Africa");
        saveCountry("Madagascar", "Africa");
        saveCountry("Malawi", "Africa");
        saveCountry("Mali", "Africa");
        saveCountry("Mauritania", "Africa");
        saveCountry("Mauritius", "Africa");
        saveCountry("Morocco", "Africa");
        saveCountry("Mozambique", "Africa");
        saveCountry("Namibia", "Africa");
        saveCountry("Niger", "Africa");
        saveCountry("Nigeria", "Africa");
        saveCountry("Rwanda", "Africa");
        saveCountry("Sao Tome and Principe", "Africa");
        saveCountry("Senegal", "Africa");
        saveCountry("Seychelles", "Africa");
        saveCountry("Sierra Leone", "Africa");
        saveCountry("Somalia", "Africa");
        saveCountry("South Africa", "Africa");
        saveCountry("South Sudan", "Africa");
        saveCountry("Sudan", "Africa");
        saveCountry("Tanzania", "Africa");
        saveCountry("Togo", "Africa");
        saveCountry("Tunisia", "Africa");
        saveCountry("Uganda", "Africa");
        saveCountry("Zambia", "Africa");
        saveCountry("Zimbabwe", "Africa");
        saveCountry("Albania", "Europe");
        saveCountry("Andorra", "Europe");
        saveCountry("Austria", "Europe");
        saveCountry("Belarus", "Europe");
        saveCountry("Belgium", "Europe");
        saveCountry("Bosnia and Herzegovina", "Europe");
        saveCountry("Bulgaria", "Europe");
        saveCountry("Croatia", "Europe");
        saveCountry("Cyprus", "Europe");
        saveCountry("Czech Republic", "Europe");
        saveCountry("Denmark", "Europe");
        saveCountry("Estonia", "Europe");
        saveCountry("Finland", "Europe");
        saveCountry("France", "Europe");
        saveCountry("Germany", "Europe");
        saveCountry("Greece", "Europe");
        saveCountry("Hungary", "Europe");
        saveCountry("Iceland", "Europe");
        saveCountry("Ireland", "Europe");
        saveCountry("Italy", "Europe");
        saveCountry("Kosovo", "Europe");
        saveCountry("Latvia", "Europe");
        saveCountry("Liechtenstein", "Europe");
        saveCountry("Lithuania", "Europe");
        saveCountry("Luxembourg", "Europe");
        saveCountry("Malta", "Europe");
        saveCountry("Moldova", "Europe");
        saveCountry("Monaco", "Europe");
        saveCountry("Montenegro", "Europe");
        saveCountry("Netherlands", "Europe");
        saveCountry("North Macedonia", "Europe");
        saveCountry("Norway", "Europe");
        saveCountry("Poland", "Europe");
        saveCountry("Portugal", "Europe");
        saveCountry("Romania", "Europe");
        saveCountry("Russia", "Europe");
        saveCountry("San Marino", "Europe");
        saveCountry("Serbia", "Europe");
        

        saveCountry("Slovakia", "Europe");
        saveCountry("Slovenia", "Europe");
        saveCountry("Spain", "Europe");
        saveCountry("Sweden", "Europe");
        saveCountry("Switzerland", "Europe");
        saveCountry("Ukraine", "Europe");
        saveCountry("United Kingdom", "Europe");
        saveCountry("Vatican City", "Europe");

        saveCountry("Argentina", "South America");
        saveCountry("Bolivia", "South America");
        saveCountry("Brazil", "South America");
        saveCountry("Chile", "South America");
        saveCountry("Colombia", "South America");
        saveCountry("Ecuador", "South America");
        saveCountry("Guyana", "South America");
        saveCountry("Paraguay", "South America");
        saveCountry("Peru", "South America");
        saveCountry("Suriname", "South America");
        saveCountry("Uruguay", "South America");
        saveCountry("Venezuela", "South America");

        saveCountry("Australia", "Oceania");
        saveCountry("Fiji", "Oceania");
        saveCountry("Kiribati", "Oceania");
        saveCountry("Marshall Islands", "Oceania");
        saveCountry("Micronesia", "Oceania");
        saveCountry("Nauru", "Oceania");
        saveCountry("New Zealand", "Oceania");
        saveCountry("Palau", "Oceania");
        saveCountry("Papua New Guinea", "Oceania");
        saveCountry("Samoa", "Oceania");
        saveCountry("Solomon Islands", "Oceania");
        saveCountry("Tonga", "Oceania");
        saveCountry("Tuvalu", "Oceania");
        saveCountry("Vanuatu", "Oceania");
        saveCountry("Antigua and Barbuda", "North America");
        saveCountry("Bahamas", "North America");
        saveCountry("Barbados", "North America");
        saveCountry("Belize", "North America");
        saveCountry("Canada", "North America");
        saveCountry("Costa Rica", "North America");
        saveCountry("Cuba", "North America");
        saveCountry("Dominica", "North America");
        saveCountry("Dominican Republic", "North America");
        saveCountry("El Salvador", "North America");
        saveCountry("Grenada", "North America");
        saveCountry("Guatemala", "North America");
        saveCountry("Haiti", "North America");
        saveCountry("Honduras", "North America");
        saveCountry("Jamaica", "North America");
        saveCountry("Mexico", "North America");
        saveCountry("Nicaragua", "North America");
        saveCountry("Panama", "North America");
        saveCountry("Saint Kitts and Nevis", "North America");
        saveCountry("Saint Lucia", "North America");
        saveCountry("Saint Vincent and the Grenadines", "North America");
        saveCountry("Trinidad and Tobago", "North America");
        saveCountry("United States of America", "North America");
        saveDestination("Oslo", "Norway", "The capital of Norway. Oslo is a city of contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a city of green spaces, with more than 50 percent of the city covered by forest and parkland. Oslo is a city of contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a city of green spaces, with more than 50 percent of the city covered by forest and parkland. Oslo is a city of contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a city of green spaces, with more than 50 percent of the city covered by forest and parkland. Oslo is a city of contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a city of green spaces, with more than 50 percent of the city covered by forest and parkland. Oslo is a city of contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a city of green spaces, with more than 50 percent of the city covered by forest and parkland. Oslo is a city of contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a city of green spaces, with more than 50 percent of the city covered by forest and parkland. Oslo is a city of contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a city of green spaces, with more than 50 percent of the city covered by forest and parkland. Oslo is a city of contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a city of green spaces, with more than 50 percent of the city covered by forest and parkland. Oslo is a city of contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a city of green spaces, with more than 50 percent of the city covered by forest and parkland. Oslo is a city of contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a city of green spaces, with more than 50 percent of the city covered by forest and parkland. Oslo is a city of contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a city of green spaces, with more than 50 percent of the city covered by forest and parkland. Oslo is a city of contrasts, with a rich cultural life and a vibrant nightlife. Oslo is also a city of green spaces, with more than 50 percent of the city covered by forest");
        saveDestination("Bergen", "Norway", "Bergen is a city and municipality in Hordaland on the west coast of Norway. Bergen is the second-largest city in Norway, the municipality covers 465 square kilometres (180 sq mi) and is home to 278,121 inhabitants. Bergen is the administrative centre of Hordaland and consists of eight boroughs: Arna, Bergenhus, Fana, Fyllingsdalen, Laksevåg, Ytrebygda, Årstad and Åsane. The city centre and northern neighbourhoods are on Byfjorden, the city's southern neighbourhoods are on the island of Bergenøya, and the western neighbourhoods are on the peninsula of Bergenshalvøyen. The city is an international centre for aquaculture, shipping, offshore petroleum industry and subsea technology, and a national centre for higher education, media, tourism and finance. Bergen Port is the busiest in Norway and one of the largest in Northern Europe. The city is an important centre for industries and maritime trade in Europe. ");
        saveDestination("Trondheim", "Norway", "Trondheim is a city and municipality in Sør-Trøndelag county, Norway. It has a population of 187,353 (as of 1 January 2018) and is the third most populous municipality in Norway. The city functions as the administrative centre of Sør-Trøndelag county. The municipality is the third largest by population in Norway and the fourth largest municipal area. Trondheim lies on the south shore of the Trondheim Fjord at the mouth of the river Nidelva. The settlement was founded in 997 as a trading post and became the capital of Norway in 1130. The city was named Strindheim in the early 12th century after the farm Strinda, which was the residence of the bishop. The name Trondheim was used first in 1217 and is derived from the Old Norse words trönd and heimr. The city's name therefore translates to crossing place(s) of the fjord(s).");
        saveDestination("Stavanger", "Norway", "Stavanger is a city and municipality in Norway. It is the fourth-largest city in the country, with a population of 122,388 as of 1 January 2018. The municipality is the third largest by population in Norway and the fourth largest municipal area. The city is the administrative centre of Rogaland county. Stavanger is the centre of the Stavanger metropolitan region, which has a population of 279,000. The city is an international centre for the oil industry and shipping, and a national centre for aquaculture, higher education, tourism and finance. Stavanger is the administrative centre of Rogaland county. The city is the centre of the Stavanger metropolitan region, which has a population of 279,000. The city is an international centre for the oil industry and shipping, and a national centre for aquaculture, higher education, tourism and finance. Stavanger is the administrative centre of Rogaland county. The city is the centre of the Stavanger metropolitan region, which has a population of 279,000. The city is an international centre for the oil industry and shipping, and a national centre for aquaculture, higher education, tourism and finance. Stavanger is the administrative centre of Rogaland county. The city is the centre of the Stavanger metropolitan region, which has a population of 279,000. The city is an international centre for the oil industry and shipping, and a national centre for aquaculture, higher education, tourism and finance. Stavanger is the administrative centre of Rogaland county. The city is the centre of the Stavanger metropolitan region, which has a population of 279,000. The city is an international centre for the oil industry and shipping, and a national centre for aquaculture, higher education, tourism and finance. Stavanger is the administrative centre of Rogaland county. The city is the centre of the Stavanger metropolitan region, which has a population of 279,000. The city is an international centre for the oil industry and shipping, and a national centre for aquaculture, higher education, tourism and finance. Stavanger is the administrative centre of Rogaland county. The city is the centre of the Stavanger metropolitan region, which has a population of 279,000. The city is an international centre for the oil industry and shipping, and a national");
        saveDestination("Tromsø", "Norway", "Tromsø is a city and municipality in Troms county, Norway. It is the most populous city in Northern Norway. The administrative centre of Troms county, Tromsø lies on the northern coast of the island of Tromsøya, about 350 kilometres (220 mi) north of the Arctic Circle. The city is the largest settlement in the county, and the 11th most populous urban area in Norway. Tromsø has a population of 69,515 (as of 1 January 2018), and is the fourth most populous urban area in the country. The city is a major centre for maritime industries and shipping, and is the location of the Arctic University of Norway. Tromsø is also a popular tourist destination, and is known for its northern lights, midnight sun, and the annual Tromsø International Film Festival. Tromsø is a city and municipality in Troms county, Norway. It is the most populous city in Northern Norway. The administrative centre of Troms county, Tromsø lies on the northern coast of the island of Tromsøya, about 350 kilometres (220 mi) north of the Arctic Circle. The city is the largest settlement in the county, and the 11th most populous urban area in Norway. Tromsø has a population of 69,515 (as of 1 January 2018), and is the fourth most populous urban area in the country. The city is a major centre for maritime industries and shipping, and is the location of the Arctic University of Norway. Tromsø is also a popular tourist destination, and is known for its northern lights, midnight sun, and the annual Tromsø International Film Festival. Tromsø is a city and municipality in Troms county, Norway. It is the most populous city in Northern Norway. The administrative centre of Troms county, Tromsø lies on the northern coast of the island of Tromsøya, about 350 kilometres (220 mi) north of the Arctic Circle. The city is the largest settlement in the county, and the 11th most populous urban area in Norway. Tromsø has a population of 69,515 (as of 1 January 2018), and is the fourth most populous urban area in the country. The city is a major centre for maritime industries and shipping, and is the location of the Arctic University of Norway.");
        saveDestination("spiterstulen", "Norway", "Spiterstulen is located 1111 meters above sea level in the lush Visdalen in Jotunheimen, and is a natural starting point for trips to Norway's highest mountain - Galdhøpiggen. Here you can also reach 17 of the 26 peaks in Norway at over 2,300 meters above sea level on a day trip. Big brother Galdhøpiggen reigns in the west with little brother Glittertind in the east. When the cabin opens in the spring, the area is an eldorado for top ski tours.");
        saveDestination("Gjendebu", "Norway", "Gjendebu is a mountain cabin in the Jotunheimen National Park, Norway. It is located at 1,200 metres (3,900 ft) above sea level, on the south side of the Gjende lake, in the Gjende valley. The cabin is open from May to October. The cabin is located at the end of the Gjendebu road, which is open to cars from the end of May to the end of September. The cabin is located at the end of the Gjendebu road, which is open to cars from the end of May to the end of September. The cabin is located at the end of the Gjendebu road, which is open to cars from the end of May to the end of September. The cabin is located at the end of the Gjendebu road, which is open to cars from the end of May to the end of September. The cabin is located at the end of the Gjendebu road, which is open to cars from the end of May to the end of September. The cabin is located at the end of the Gjendebu road, which is open to cars from the end of May to the end of September. The cabin is located at the end of the Gjendebu road, which is open to cars from the end of May to the end of September. The cabin is located at the end of the Gjendebu road, which is open to cars from the end of May to the end of September. The cabin is located at the end of the Gjendebu road, which is open to cars from the end of May to the end of September. The cabin is located at the end of the Gjendebu road, which is open to cars from the end of May to the end of September. The cabin is located at the end of the Gjendebu road, which is open to cars from the end of May to the end of September. The cabin is located at the end of the Gjendebu road, which is open to cars from the end of May to the end of September. The cabin is located at the end of the Gjendebu road, which is open to cars from the end of May to the end of September. The cabin is located at the end of the Gjendebu road,");
        saveDestination("Finse", "Norway", "Finse is a mountain village area on the shore of the lake Finsevatnet in Ulvik municipality in Vestland county, Norway. The village is centered on Finse Station, a railway station on the Bergen Line. The village sits at an elevation of 1,222 metres (4,009 ft) above sea level, making it the highest station on the entire Norwegian railway system. ");

        saveDestination("Mount Everest", "Nepal", "Mount Everest, also known in Nepal as Sagarmatha and in China as Chomolungma/珠穆朗玛峰, is Earth's highest mountain above sea level, located in the Mahalangur Himal sub-range of the Himalayas. The China–Nepal border runs across its summit point. Mount Everest is 8,848 metres (29,029 ft) tall, which is 2.23 metres (7.3 ft) less than the previously accepted height of 8,850 metres (29,035 ft). The international community recognizes the height of 8,848 metres (29,029 ft) as the official height of Mount Everest. The current official height of 8,848 metres (29,029 ft) was established by a 1955 Indian survey and confirmed by a 1975 Chinese survey. The height was most recently revised in 1999 by a Nepalese survey, which found that the mountain was 2.23 metres (7.3 ft) shorter than the previous height. The mountain was named after Sir George Everest, who was the Surveyor General of India from 1830 to 1843. The first recorded ascent of Everest was in 1953 by New Zealand mountaineer Edmund Hillary and Sherpa Tenzing Norgay. ");
        saveDestination("Kilimanjaro", "Tanzania", "Kilimanjaro, also known as Kibo, is a dormant volcano in Tanzania. It is the highest mountain in Africa, and rises approximately 4,900 metres (16,000 ft) from its base to 5,895 metres (19,341 ft) above sea level. It is the highest point in both Tanzania and Kenya. The first recorded ascent of Kilimanjaro was in 1889 by Hans Meyer and Ludwig Purtscheller. The mountain is a popular climbing destination, with more than 45,000 people attempting to reach the summit each year. "); 
        saveDestination("Aconcagua", "Argentina", "Aconcagua is the highest mountain in the Americas and the highest mountain outside Asia. It is located in the Mendoza Province, in the Argentinean Andes, about 80 kilometres (50 mi) northwest of the provincial capital, Mendoza. The mountain lies on the border between Argentina and Chile. The mountain is named after Aconcagua, a mythical giant in the Andean mythology. The first ascent of Aconcagua was made by a Swiss expedition led by Edward Whymper in 1897. ");
        saveDestination("Denali", "United States", "Denali is the highest mountain peak in North America, with a summit elevation of 20,310 feet (6,190 m) above sea level. Denali is located in the Denali National Park and Preserve in Alaska, United States, about 150 miles (240 km) south of the Arctic Circle. The mountain was formerly known as Mount McKinley, but its native name was restored by presidential proclamation in 2015. "); 
        saveDestination("Vesuvius", "Italy", "Mount Vesuvius is a somma-stratovolcano located on the Gulf of Naples in Campania, Italy, about 9 km (5.6 mi) east of Naples and a short distance from the shore. It is one of several volcanoes which form the Campanian volcanic arc. Vesuvius consists of a large cone partially encircled by the steep rim of a summit caldera caused by the collapse of an earlier and originally much higher structure. Vesuvius is best known for its eruption in AD 79 that led to the burying and destruction of the Roman cities of Pompeii and Herculaneum. ");
        saveDestination("Sisily", "Italy", "Mount Etna is an active stratovolcano on the east coast of Sicily, Italy, in the Metropolitan City of Catania, between the cities of Catania and Messina. It is the tallest active volcano in Europe, currently 3,329 m (10,922 ft) high, though this varies with summit eruptions. It is the highest peak in Italy south of the Alps. "); 
        saveDestination("Paris", "France", "Paris is the capital and most populous city of France. It is situated on the Seine River, in the north of the country, at the heart of the Île-de-France region. It is the centre of the Paris metropolitan area, which has an estimated population of 12.2 million, or 18.4 million if including the densely populated Île-de-France region around the city. ");
        saveDestination("London", "United Kingdom", "London is the capital and largest city of England and the United Kingdom. Standing on the River Thames in the south-east of England, at the head of its 50-mile (80 km) estuary leading to the North Sea, London has been a major settlement for two millennia. Londinium was founded by the Romans. ");
        saveDestination("New York", "United States", "New York is the most populous city in the United States. With an estimated 2019 population of 8,336,817 distributed over a land area of about 302.6 square miles (784 km2), New York is also the most densely populated major city in the United States. Located at the southern tip of the state of New York, the city is the center of the New York metropolitan area, the largest metropolitan area in the world by urban landmass and one of the world's most populous megacities, with an estimated 2019 population of 20,321,000. ");
        saveDestination("Tokyo", "Japan", "Tokyo is the capital of Japan, the center of the Greater Tokyo Area, and the most populous metropolitan area in the world. It is the seat of the Japanese government and the Imperial Palace, and the home of the Japanese Imperial Family. Tokyo is in the Kantō region on the southeastern side of the main island Honshu and includes the Izu Islands and Ogasawara Islands. Tokyo Metropolis was formed in 1943 from the merger of the former Tokyo Prefecture (東京府, Tōkyō-fu) and the city of Tokyo (東京市, Tōkyō-shi). Tokyo Metropolis was dissolved on March 31, 1947, and Tokyo was reestablished as a prefecture. Tokyo is often referred to as a city, but is officially known and governed as a 'metropolitan prefecture', which differs from and combines elements of a city and a prefecture, a characteristic unique to Tokyo. ");
        saveDestination("Sydney", "Australia", "Sydney is the state capital of New South Wales and the most populous city in Australia and Oceania. Located on Australia's east coast, the metropolis surrounds Port Jackson and extends about 70 km (43.5 mi) on its periphery towards the Blue Mountains to the west, Hawkesbury to the north, the Royal National Park to the south and Macarthur to the south-west. Sydney is made up of 658 suburbs, 40 local government areas and 15 contiguous regions. Residents of the city are known as 'Sydneysiders'. As of June 2019, Sydney's estimated metropolitan population was 5,230,330 and is home to approximately 65% of the state's population. ");
        saveDestination("Rio de Janeiro", "Brazil", "Rio de Janeiro, or simply Rio, is the second-most populous municipality in Brazil and the sixth-most populous in the Americas. The metropolis is anchor to the Rio de Janeiro metropolitan area, the second-most populous metropolitan area in Brazil and sixth-most populous in the Americas. Rio de Janeiro is the capital of the state of Rio de Janeiro, Brazil's third-most populous state. Part of the city has been designated as a World Heritage Site, named 'Rio de Janeiro: Carioca Landscapes between the Mountain and the Sea', by UNESCO on 1 July 2012 as a Cultural Landscape. ");
        saveDestination("Cairo", "Egypt", "Cairo is the capital of Egypt. It is the largest city in the Arab world, the largest city in Africa, and the Middle East, and the 15th-largest city in the world. The city's metropolitan area is the 16th-largest in the world and is associated with ancient Egypt, as the famous Giza pyramid complex and the ancient city of Memphis are located in its geographical area. ");
        saveDestination("Moscow", "Russia", "Moscow is the capital and most populous city of Russia, with 12.4 million residents within the city limits, 17.1 million within the urban area, and 20.5 million within the metropolitan area. The city stands on the Moskva River in the Central Federal District of European Russia, making it Europe's most populated inland city. The city's history spans more than 850 years, and it has played a significant role in the development of Russia and the world. Moscow is the northernmost and coldest megacity and metropolis on Earth. ");
        saveDestination("Beijing", "China", "Beijing is the capital of the People'sRepublic of China and one of the most populous cities in the world. Thepopulation as of 2019 was 21,516,000. The metropolis, located in northernChina, is governed as a direct-controlled municipality under the nationalgovernment with 16 urban, suburban, and rural districts. Beijing Municipalityis surrounded by Hebei Province with the exception of neighboring TianjinMunicipality to the southeast. ");
        saveDestination("Shanghai", "China", "Shanghai is the most populous city proper in the world, with a population of more than 24 million as of 2019. As one of the four direct-controlled municipalities of the People's Republic of China, it is a global city and financial centre with the second highest number of skyscrapers in the world. It is also the world's busiest container port and has the world's busiest airport by passenger traffic. ");
        saveDestination("Seoul", "South Korea", "Seoul is the capital and largest metropolis of South Korea. A megacity with a population of over 10 million, it is the world's 4th largest metropolitan area and the world's 5th largest city proper. The Seoul Capital Area, which includes the surrounding Incheon metropolis and Gyeonggi province, is the world's 5th largest metropolitan area with over 25 million people. ");
        saveDestination("Bangkok", "Thailand", "Bangkok is the capital and mostpopulous city of Thailand. It is known in Thai as Krung Thep Maha Nakhon orsimply Krung Thep. The city occupies 1,568.7 square kilometres (605.7 sq mi)in the Chao Phraya River delta in Central Thailand, and has a population ofover eight million, or 12.6 percent of the country's population. Overfourteen million people (22.2 percent) live within the surrounding BangkokMetropolitan Region, making Bangkok an extreme primate city, significantlydwarfing Thailand's other urban centres in terms of importance. ");
        saveDestination("Hong Kong", "Hong Kong", "Hong Kong, officially the Hong Kong Special Administrative Region of the People's Republic of China, is an autonomous territory on the eastern side of the Pearl River estuary in East Asia. With over 7.4 million people of various nationalities in a 1,104-square-kilometre (426 sq mi) territory, Hong Kong is the world's fourth most densely populated area. It is the most populous city in China, with 7.27 million people in a 1,104-square-kilometre (426 sq mi) territory. Hong Kong is a Special Administrative Region (SAR) of the People's Republic of China. ");
        saveDestination("Singapore", "Singapore", "Singapore, officially the Republic of Singapore, is a sovereign city-state and island country in Southeast Asia. It lies one degree of latitude (137 kilometres or 85 miles) north of the equator, at the southern tip of the Malay Peninsula, with Indonesia's Riau Islands to the south and Peninsular Malaysia to the north. Singapore's territory consists of one main island along with 62 other islets. Since independence, extensive land reclamation has increased its total size by 23% (130 km2 or 50 sq mi). ");
        saveDestination("Taipei", "Taiwan", "Taipei, officially known as TaipeiCity, is the capital and a special municipality of Taiwan (officially theRepublic of China). It is the political, economic, educational, and culturalcenter of Taiwan. The Taipei City Government is currently the largest citygovernment in the world, with a total population of 2.98 million. Taipei is amajor hub of transportation in the region, and is home to the Taipei SongshanAirport, Taiwan Taoyuan International Airport, and Taipei Port. ");

    }

    //Combined SQL
       //     "SELECT * FROM Itinerary INNER JOIN Itinerary_destination ON (id = itinerary_id)"
        //   + "WHERE destination_name LIKE '%' || :keyword || '%' "
         //   + "OR country LIKE '%' || :keyword || '%'"
         // + "OR title LIKE '%' || :keyword || '%' "
          //  + "OR description LIKE '%' || :keyword || '%'";


////////////////////////////////////////////////////////////////////////////////////////////////
// Recommended itineraries

public List<Integer> getLikedOrRatedItineraries(String userEmail) throws SQLException {
    Connection conn = null;
    PreparedStatement statement = null;
    ResultSet resultset = null;
    List<Integer> itineraryIDs = new ArrayList<>();

    try{ 
        conn = connectToDB();
        String sqlQuery = "SELECT itinerary_id FROM Rating WHERE user_email = ? AND rating > 3 " +
        "UNION SELECT itinerary_id FROM Liked_Itineraries WHERE user_email = ?";
        statement = conn.prepareStatement(sqlQuery);

        statement.setString(1, userEmail);
        statement.setString(2, userEmail);

        resultset = statement.executeQuery();
        while (resultset.next()) {
            int itineraryID = resultset.getInt("itinerary_id");
            itineraryIDs.add(itineraryID);
        }
    } catch (SQLException e) {
        throw new SQLException(e);
    }
    finally {
        if (statement != null) {
            statement.close();
        }
        if (resultset != null) {
            resultset.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    return itineraryIDs;
}

public List<Itinerary> getRandomItineraries(int numberOfItineraries, String userEmail) throws SQLException {
    Connection conn = null;
    PreparedStatement statement = null;
    ResultSet resultset = null;
    List<Itinerary> itineraries = new ArrayList<>();

    try {
        conn = connectToDB();
        String sqlQuery = "SELECT * FROM Itinerary WHERE "
        + "id NOT IN (SELECT id FROM Itinerary WHERE writer_email = ?) "
        + "AND id NOT IN (SELECT itinerary_id FROM Rating WHERE user_email = ?) "
        + "AND id NOT IN (SELECT itinerary_id FROM Liked_Itineraries WHERE user_email = ?) "
        + "ORDER BY RANDOM() LIMIT " + numberOfItineraries;
        statement = conn.prepareStatement(sqlQuery);
        statement.setString(1, userEmail);
        statement.setString(2, userEmail);
        statement.setString(3, userEmail);

        resultset = statement.executeQuery();

        while (resultset.next()) {
            Itinerary itinerary = new Itinerary(-1, null, null, 0, null, null, null, 0);
            itinerary.mapItineraryFromResultSet(resultset);
            itineraries.add(itinerary);
        }
    } catch (SQLException e) {
        throw new SQLException(e);
    } finally {
        if (statement != null) {
            statement.close();
        }
        if (resultset != null) {
            resultset.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
    return itineraries;
}


public List<Itinerary> getRecommendedItineraries(String userEmail) throws SQLException {
    
    Connection conn = null;
    PreparedStatement statement = null;
    ResultSet resultset = null;
    List<Integer> likedOrRatedItineraryIDs = getLikedOrRatedItineraries(userEmail); // get liked/rated itineraries by user
    List<Itinerary> recommendedItineraries = new ArrayList<>();

    
    if (likedOrRatedItineraryIDs.isEmpty()) {
        // if user has not liked or rated any itineraries, return ten random itineraries
        return getRandomItineraries(10, userEmail);
    }

    String sqlQuery = "SELECT DISTINCT i.id, i.title, i. writer_email, i.written_date, i.estimated_time, i.itinerary_description, i.image, i.cost, COUNT(*) AS num_common_destinations "
                + "FROM Itinerary_Destination id1 "
                + "JOIN Itinerary_Destination id2 ON id1.destination_name = id2.destination_name AND id1.country = id2.country "
                + "JOIN Itinerary i ON id2.itinerary_id = i.id "
                + "WHERE id1.itinerary_id IN (" + String.join(",", Collections.nCopies(likedOrRatedItineraryIDs.size(), "?")) + ") "
                + "AND id2.itinerary_id NOT IN (" + String.join(",", Collections.nCopies(likedOrRatedItineraryIDs.size(), "?")) + ") "
                + "AND id2.itinerary_id NOT IN (SELECT itinerary_id FROM Rating WHERE user_email = ?) "
                + "AND id2.itinerary_id NOT IN (SELECT itinerary_id FROM Liked_Itineraries WHERE user_email = ?) "
                + "AND id2.itinerary_id NOT IN (SELECT id FROM Itinerary WHERE writer_email = ?) "
                + "GROUP BY i.id "
                + "HAVING COUNT(*) >= 1 "
                + "ORDER BY num_common_destinations DESC "
                + "LIMIT 10";

    try {
        conn = connectToDB();
        statement = conn.prepareStatement(sqlQuery);

        // set parameters for liked/rated itinerary IDs
        for (int i = 0; i < likedOrRatedItineraryIDs.size(); i++) {
            statement.setInt(i + 1, likedOrRatedItineraryIDs.get(i));
        }
        // set parameters for exclusion of liked/rated itinerary IDs
        for (int i = 0; i < likedOrRatedItineraryIDs.size(); i++) {
            statement.setInt(i + likedOrRatedItineraryIDs.size() + 1, likedOrRatedItineraryIDs.get(i));
        }
        // set user email parameter for exclusion of rated itineraries
        statement.setString(2 * likedOrRatedItineraryIDs.size() + 1, userEmail);
        // set user email parameter for exclusion of liked itineraries
        statement.setString(2 * likedOrRatedItineraryIDs.size() + 2, userEmail);
        statement.setString(2 * likedOrRatedItineraryIDs.size() + 3, userEmail);

        resultset = statement.executeQuery();

        while (resultset.next()) {
            Itinerary itinerary = new Itinerary(-1, null, null, 0, null, null, null, 0);
            itinerary.mapItineraryFromResultSet(resultset);
            recommendedItineraries.add(itinerary);
        }

    } catch (SQLException e) {
        throw new SQLException(e);
    } 

    finally {
        if (statement != null) {
            statement.close();
        }
        if (resultset != null) {
            resultset.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    if(recommendedItineraries.isEmpty()) {
        recommendedItineraries = getRandomItineraries(10, userEmail);
    } else if (recommendedItineraries.size() < 10) {
        Set<Itinerary> holdingSet = new HashSet<>();
        holdingSet.addAll(recommendedItineraries);
        int i = 0;
        while (holdingSet.size() < 10 && i != 5) {
            holdingSet.addAll(getRandomItineraries(10 - recommendedItineraries.size(), userEmail));
            i++;
            recommendedItineraries.clear();
            recommendedItineraries.addAll(holdingSet);
        }
    }
    return recommendedItineraries;  
    }

    public void saveRating(String userEmail, int itineraryID, int rating) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = connectToDB();
            String sqlQuery = "INSERT INTO Rating(user_email, itinerary_id, rating) VALUES (?, ?, ?)";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, userEmail);
            statement.setInt(2, itineraryID);
            statement.setInt(3, rating);

            if (hasUserRatedItinerary(userEmail, itineraryID)) {
                deleteRating(userEmail, itineraryID);
            }
            statement.executeUpdate();
            System.out.println("SAVE RATING RECIEVED IN REPO");

        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    // Check if user already has given rating
    private boolean hasUserRatedItinerary(String userEmail, int itineraryID) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;
        boolean result = false;

        try {
            conn = connectToDB();
            String sqlQuery = "SELECT * FROM Rating WHERE user_email = ? AND itinerary_id = ?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, userEmail);
            statement.setInt(2, itineraryID);
            resultset = statement.executeQuery();

            if (resultset.next()) {
                result = true;
            }

        } catch (SQLException e) {
            throw new SQLException(e);

        } finally {
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                // do nothing
            }
        }
        return result;
    }

    public void deleteRating(String userEmail, int itineraryID) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = connectToDB();
            String sqlQuery = "DELETE FROM Rating WHERE user_email = ? AND itinerary_id = ?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, userEmail);
            statement.setInt(2, itineraryID);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);

        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                // do nothing
            }
        }
    }

    public int getUserRatingOnItinerary(String userEmail, int ItineraryID) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;
        int rating = 0;

        try {
        conn = connectToDB();
        String sqlQuery = "SELECT rating FROM Rating WHERE user_email = ? AND itinerary_id = ?";
        statement = conn.prepareStatement(sqlQuery); 
        statement.setString(1, userEmail);
        statement.setInt(2, ItineraryID);
        resultset = statement.executeQuery(); 
            if (resultset.next()) {
                rating = resultset.getInt("rating");
            }
        
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        finally {
            if (resultset != null) {
                resultset.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return rating;
    }

    public double getItineraryAverageRating(int itineraryID) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;
        Double averageRating = 0.0;
        int ratingCount = 0;
        
        try {
            conn = connectToDB();
            String sqlQuery = "SELECT rating FROM Rating WHERE itinerary_id = ?";
            statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, itineraryID);
            resultset = statement.executeQuery();
            
            while (resultset.next()) {
                averageRating += resultset.getInt("rating");
                ratingCount++;
            }
            
            if (ratingCount > 0) {
                averageRating /= ratingCount;
            }

        } finally {
            if (resultset != null) {
                resultset.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        
        return averageRating;
    }

    public List<Itinerary> loadRatedItineraries(String userEmail) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Itinerary> ratedItineraries = new ArrayList<>();

        try {
            conn = connectToDB();
            String sqlQuery = "SELECT * FROM Itinerary INNER JOIN Rating r ON (id = itinerary_id) WHERE r.user_email = ?";

            statement = conn.prepareStatement(sqlQuery);
            statement.setString(1, userEmail);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Itinerary itinerary = new Itinerary(-1, null, null, 0, null, null, null, 0);
                itinerary.mapItineraryFromResultSet(resultSet);
                ratedItineraries.add(itinerary);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        
        return ratedItineraries;
    }

}



