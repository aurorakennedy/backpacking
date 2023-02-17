package group61.backpacking;

//@Entity
public class User {

    //@ID
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String email;
    private String password;
    private String userName;


    public User(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getUserName() {
        return userName;
    }
    
    
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    



}




