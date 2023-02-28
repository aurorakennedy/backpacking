package group61.backpacking;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.InputStream;
import java.sql.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public class ItineraryRepository {


    ///////////////////////////////////////////////////////////////////////////
    // helper functions


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

    // not really helpful yet
    public Date getDate(){
        // find out how to get current date

        return new Date(2020, 12, 12);

    }




    ///////////////////////////////////////////////////////////////////////////
    // save functions

    
    public void saveItineraryAndDestinations(User user, String title, String destination, Integer order, Itinerary itinerary ) throws SQLException{
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        

        try {
            
            conn = connectToDB();
            String sqlQuery = "INSERT INTO itinerary_destination (itinerary_id, destination_name, order_number) VALUES (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            //db.update(preparedStatement, user.getUserName(), user.getPassword(), user.getEmail());
            preparedStatement.setInt(1, itinerary.getId());
            preparedStatement.setString(2, destination);
            preparedStatement.setInt(3, order);
            
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


    // Todo: time to apropriate dataType
    public void saveItinerary(User user, String estimatedTime, String description, InputStream image, String title, List<String> destinationsList) throws SQLException{
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            if (validateItinerary(title, user.getEmail()) == false){
                throw new SQLException("Itinerary with this title already exists");
                
            }
            
            conn = connectToDB();
            String sqlQuery = "INSERT INTO Itinerary ( writer_email, written_date, estimated_time, itinerary_description, image, title ) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlQuery);
            //db.update(preparedStatement, user.getUserName(), user.getPassword(), user.getEmail());
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setDate(2, getDate());
            preparedStatement.setString(3, estimatedTime);
            preparedStatement.setString(4, description);
            preparedStatement.setBinaryStream(5, image);
            preparedStatement.setString(6, title);
            
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

        Itinerary itinerary = new Itinerary(-1, null, null, -1, null, null, null);

        try {
            itinerary = loadItineraryByInput(title, user.getEmail());
        } catch (Exception e) {
                // do nothing
        }
        System.out.println(itinerary.toString());

        try {

            for (int i = 0; i < destinationsList.size(); i++) {
                saveItineraryAndDestinations(user, title, destinationsList.get(i), i+1, itinerary);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
            


    }

    ///////////////////////////////////////////////////////////////////////////
    // validate functions

    public boolean validateItinerary(String title, String email) throws RuntimeException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Itinerary itinerary = new Itinerary(-1, null,null,-1,null,null,null);

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
        try {
            conn.close();
            statement.close();
            resultSet.close();
        } catch (Exception e) {
            // do nothing
        }
        if(itinerary.getId() == -1){
            return true;
        }
        return false;
    }


    ///////////////////////////////////////////////////////////////////////////
    // load functions



    public Itinerary loadItineraryByInput(String title, String email) throws RuntimeException, SQLException {

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Itinerary itinerary = new Itinerary(-1, null,null,-1,null,null,null);

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
        try {
            conn.close();
            statement.close();
            resultSet.close();
        } catch (Exception e) {
            // do nothing
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
                Itinerary itinerary = new Itinerary(-1, null, null, -1, null, null, null);  // TODO: kostruktør i Itinerary
                itinerary.mapItineraryFromResultSet(resultSet);
                // TODO: Legge til destinations
                itineraries.add(itinerary);
            }

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                // handle exception
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
                Itinerary itinerary = new Itinerary(0, null, null, (Integer) null, null, null, null);
                itinerary.mapItineraryFromResultSet(resultSet);
                itineraryList.add(itinerary);
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


    


    



    
    ///////////////////////////////////////////////////////////////////////////
    // update / delete functions

    // sletting basert på tittel og email
    public void deleteItinerary_byEmail(User user, String title) throws SQLException{
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

        try {
            preparedStatement.close();
            conn.close();   
        } catch (RuntimeException e) {}
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



}



