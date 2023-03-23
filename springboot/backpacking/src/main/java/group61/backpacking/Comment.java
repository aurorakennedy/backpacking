package group61.backpacking;

public class Comment {
    int id;
    int itineraryId;
    String author;
    String content;
    
    public Comment(int id, int itineraryId, String author, String content) {
        this.id = id;
        this.itineraryId = itineraryId;
        this.author = author;
        this.content = content;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getItineraryId() {
        return itineraryId;
    }
    public void setItineraryId(int itineraryId) {
        this.itineraryId = itineraryId;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
}
