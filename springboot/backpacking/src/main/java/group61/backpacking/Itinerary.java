package group61.backpacking;

import java.io.InputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import javax.print.attribute.standard.Destination;

import jakarta.websocket.Decoder.BinaryStream;

public class Itinerary {
    
    private int id;
    private String writerEmail;
    private Date writtenDate;
    private int estimatedTime;
    private String description;
    private String image;
    private String title;
    private int cost;

    public Itinerary(int id, String writerEmail, Date writtenDate, int estimatedTime, String description, String image, String title) {
        this.id = id;
        this.writerEmail = writerEmail;
        this.writtenDate = writtenDate;
        this.estimatedTime = estimatedTime;
        this.description = description;
        this.image = image;
        this.title = title;
    }

    public void mapItineraryFromResultSet(ResultSet resultSet) throws SQLException {
        

        setId(resultSet.getInt("id"));
        setWriterEmail(resultSet.getString("writer_email"));
        setWrittenDate(resultSet.getDate("written_date"));
        setEstimatedTime(resultSet.getInt("estimated_time"));
        setDescription(resultSet.getString("itinerary_description"));
        setImage(resultSet.getString("image"));
        setTitle(resultSet.getString("title"));
        
        
    }

    public int getId() {
        return id;
    }

    public String getWriterEmail() {
        return writerEmail;
    }

    public Date getWrittenDate() {
        return writtenDate;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWriterEmail(String writerEmail) {
        this.writerEmail = writerEmail;
    }

    public void setWrittenDate(Date writtenDate) {
        this.writtenDate = writtenDate;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image =  image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Itinerary [id=" + id + ", writerEmail=" + writerEmail + ", writtenDate=" + writtenDate
                + ", estimatedTime=" + estimatedTime + ", description=" + description + ", image=" + image + ", title="
                + title + ", cost=" + cost + "]";
    }

}