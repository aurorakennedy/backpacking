package group61.backpacking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackPackingController {

    @Autowired
    private BackPackingRepository rep;

    @CrossOrigin(origins = "*")
    @CrossOrigin(origins = "*")
    @PostMapping("/save")
    public User saveUser(User inUser) {
        try {
            return rep.saveUser(inUser);

        } catch (Exception e) {
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @CrossOrigin(origins = "*")
    @GetMapping("/load")
    public User loadUser(String email) {
        try {
            return rep.loadUser(email);
        } catch (Exception e) {
            return null;
        }

    }

    @CrossOrigin(origins = "*")
    @GetMapping("/delete")
    public void deleteUser(User inUser) {
        rep.deleteUser(inUser);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        System.out.println("Login request recieved on backend.");
        try {
            return rep.login(user);
        } catch (Exception e) {
            System.out.println(e.toString());
            // TODO: handle exception
            return null;
        }
    }

    // Suggestions:

    // Create a new user
    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public void register(@RequestBody User user) {
        User savedUser = rep.saveUser(user);
        System.out.println(user.getUserName());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
    }

    /*
     * @PostMapping("/login")
     * public boolean login_2(@RequestBody User user) {
     * return rep.login(user);
     * //boolean loggedIn = false;
     * //return loggedIn;
     * }
     */

    /*
     * @DeleteMapping("/users/{id}")
     * public void deleteTheUser(@RequestBody User user) {
     * 
     * public void deleteUser(@PathVariable int id) {
     * // rep.deleteAllByIdInBatch(Iterable<ID> ids)
     * }
     */

    @CrossOrigin(origins = "*")
    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User user) {

    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id, @RequestBody User user) {
        // rep.deleteAllByIdInBatch(Iterable<ID> ids)
        rep.deleteUser(user);
    }

    @PostMapping("/users/{id}")
    public Boolean updateUser(@RequestBody String password, @RequestBody String userName, @RequestBody User user) {

        User updatedUser = rep.updateUser(user, password, userName);

        if (user.getUserName() != updatedUser.getUserName()) {
            return false;
        }
        if (user.getPassword() != updatedUser.getPassword()) {
            return false;
        }
        return true;
    }

    // public void updateUser(){
    // User user = rep.loadUser(email).orElseThrow(() -> new
    // ResourceNotFoundException("User not exist with id: " + id));

    @GetMapping("/users/{id}")
    public User getUserById(@RequestBody User user) {
        return rep.loadUser(user.getEmail());

        // return new User("test@test.no", "123", "Jarl");
    }

}
