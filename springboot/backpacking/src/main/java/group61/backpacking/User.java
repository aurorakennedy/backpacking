package group61.backpacking;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

//@Entity
public class User {

    //@ID
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String email;
    private String password;
    private String username;

    @JsonCreator
    public User(@JsonProperty("email") String email,
                @JsonProperty("password") String password,
                @JsonProperty("username") String username) {
        this.email = email;
        this.password = password;
        this.username = username;
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




