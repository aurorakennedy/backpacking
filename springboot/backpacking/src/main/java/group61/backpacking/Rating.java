package group61.backpacking;

public class Rating {
    
    private String useremail;
    private Integer itinerary_id;
    private Integer rating;
    private String rating_comment;


    public Rating(String email, int itinerary_id, int rating, String rating_comment){
        this.useremail = email;
        this.itinerary_id = itinerary_id;
        this.rating = rating;
        this.rating_comment = rating_comment;
    }

    //Get
    public String getemail(){
        return useremail;
    }
    public Integer getItineraryID(){
        return itinerary_id;
    }
    public Integer getRating(){
        return rating;
    }
    public String getRatingComment(){
        return rating_comment;
    }

    //Set
    public void setemail(String email){
        this.useremail = email;
    }
    public void setItineraryID(int itineraryID){
        this.itinerary_id = itineraryID;
    }
    public void setRating(int rating){
        this.rating = rating;
    }
    public void setRatingComment(String ratingcomment){
        this.rating_comment = ratingcomment;
    }
}
