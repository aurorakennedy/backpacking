package group61.backpacking;

import java.util.List;

public class ItineraryDestination {

    private Itinerary itinerary;
    private List<Destination> destinations;

    public ItineraryDestination(Itinerary itinerary, List<Destination> destinations) {
        this.itinerary = itinerary;
        this.destinations = destinations;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }

    public void print(){
        System.out.println("title : "+ itinerary.getTitle() + "    id : " +  itinerary.getId() + " : ");
        for (Destination destination : destinations) {
            System.out.println("      " + destination.getDestinationName());
        }
    }

    
}
