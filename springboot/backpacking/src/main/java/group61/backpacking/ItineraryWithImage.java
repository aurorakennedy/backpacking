package group61.backpacking;

public class ItineraryWithImage {

    private Itinerary itinerary;
    private byte[] imageByteArray;

    public ItineraryWithImage(Itinerary itinerary, byte[] imageByteArray) {
        this.itinerary = itinerary;
        this.imageByteArray = imageByteArray;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public byte[] getImageByteArray() {
        return imageByteArray;
    }

    public void setImageByteArray(byte[] imageByteArray) {
        this.imageByteArray = imageByteArray;
    }

    
}
