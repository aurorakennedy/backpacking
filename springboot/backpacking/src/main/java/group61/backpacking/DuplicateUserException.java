package group61.backpacking;

public class DuplicateUserException extends RuntimeException {
   
    public DuplicateUserException(String note) {
        super(note);
    }
}
