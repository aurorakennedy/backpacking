package group61.backpacking;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Destination {

    private String destinationName;
    private String country;
    private String description;



    public Destination(String destinationName, String country, String description) {
        this.destinationName = destinationName;
        this.country = country;
        this.description = description;
    }

    public void mapDestinationFromResultSet(ResultSet resultSet) throws SQLException {
        
        setDestinationName(resultSet.getString("destination_name"));
        setCountry(resultSet.getString("country"));
        setDescription(resultSet.getString("destination_description"));
        
        
    }



    public String getDestinationName() {
        return destinationName;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString(){
        return "||Destination Name: " + destinationName + " Country: " + country + " Description: " + description + "\n"; 
    }




    
}
