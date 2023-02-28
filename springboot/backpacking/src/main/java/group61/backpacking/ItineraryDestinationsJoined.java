package group61.backpacking;

public class ItineraryDestinationsJoined {
    
    private int itineraryId;
    private String destinationName;
    private String country;
    private int order;
    private String description;
    
    public ItineraryDestinationsJoined(int itineraryId, String destinationName, String country, int order,
            String description) {
        this.itineraryId = itineraryId;
        this.destinationName = destinationName;
        this.country = country;
        this.order = order;
        this.description = description;
    }

    public int getItineraryId() {
        return itineraryId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public String getCountry() {
        return country;
    }

    public int getOrder() {
        return order;
    }

    public String getDescription() {
        return description;
    }
}