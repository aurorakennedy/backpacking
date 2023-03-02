package group61.backpacking;

public class ItineraryDestination {
    
    private int itineraryId;
    private String destinationName;
    private String country;
    private int order;
    
    public ItineraryDestination(int itineraryId, String destinationName, String country, int order,String description) {
        this.itineraryId = itineraryId;
        this.destinationName = destinationName;
        this.country = country;
        this.order = order;
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
}