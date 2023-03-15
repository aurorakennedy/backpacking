package group61.backpacking;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Comment {
    String userEmail;
    String comment;
    int itineraryId;


    public String getUserEmail() {
        return userEmail;
    }
    public String getComment() {
        return comment;
    }
    public int getItineraryId() {
        return itineraryId;
    }
    public Comment(String userEmail, String comment, int itineraryId) {
        this.userEmail = userEmail;
        this.comment = comment;
        this.itineraryId = itineraryId;
    }

    public void mapCommentFromResultSet(ResultSet resultSet) throws SQLException {
        this.userEmail = resultSet.getString("user_email");
        this.itineraryId = resultSet.getInt("itinerary_id");
        this.comment = resultSet.getString("comment");
    }

    
}
