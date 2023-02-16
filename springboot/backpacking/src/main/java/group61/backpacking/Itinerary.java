package group61.backpacking;

import java.util.List;

import javax.print.attribute.standard.Destination;

public class Itinerary {
    
    private double averageRating;
    private String title;
    private List<Destination> destinaions;
    
    public double getAverageRating() {
        return averageRating;
    }
    public String getTitle() {
        return title;
    }
    public List<Destination> getDestinaions() {
        return destinaions;
    }
    
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void addDestination(Destination destination) {
        destinaions.add(destination);
    }

    public void addRating(int rating) {
        this.averageRating = (averageRating + rating) / 2;
    }

}
