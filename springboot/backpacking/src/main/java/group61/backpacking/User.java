package group61.backpacking;

import java.sql.ResultSet;
import java.sql.SQLException;

//@Entity
public class User {

    //@ID
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String email;
    private String password;
    private String username;


    public User(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.username = userName;
    }

    public void mapUserFromResultSet(ResultSet resultSet) throws SQLException {
        
        
        setEmail(resultSet.getString("email"));
        setPassword(resultSet.getString("password"));
        setUsername(resultSet.getString("username"));
        
        
    }

    

    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getUsername() {
        return username;
    }
    
    
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setUsername(String userName) {
        this.username = userName;
    }

    public String toString() {
        return "User [email=" + email + ", password=" + password + ", username=" + username + "]";
    }


}




