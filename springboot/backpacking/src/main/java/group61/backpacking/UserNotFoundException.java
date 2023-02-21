package group61.backpacking;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String note) {
        super(note);
    }
}
