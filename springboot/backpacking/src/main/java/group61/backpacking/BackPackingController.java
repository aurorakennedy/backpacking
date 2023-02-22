package group61.backpacking;

import java.sql.SQLException;
import java.util.ArrayList;
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
    private UserRepository UserRep;

    // @CrossOrigin(origins = "*")
    // @PostMapping("/save")
    // public User saveUser(User inUser) {
    // try {
    // return rep.saveUser(inUser);

    // } catch (Exception e) {
    // return null;
    // }
    // }

    @CrossOrigin(origins = "*")
    @GetMapping("/load")
    public User loadUser(String email) {
        try {
            return UserRep.loadUser(email);
        } catch (Exception e) {
            return null;
        }

    }

    @CrossOrigin(origins = "*")
    @GetMapping("/delete")
    public void deleteUser(User inUser) throws RuntimeException, SQLException {
        UserRep.deleteUser(inUser);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        System.out.println("Login request recieved on backend.");
        try {
            return UserRep.login(user);
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
    public User register(@RequestBody User user) throws SQLException, RuntimeException {
        try {
            User savedUser = UserRep.saveUser(user);
            System.out.println("controller output savedUser:  " + savedUser.toString());
            System.out.println("//////////////////////////////////////////////////////////////////////////////////");
            return savedUser;
        } catch (Exception e) {
            System.out.println(e.toString());
            // TODO: handle exception
            return null;
        }
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
    public void deleteUser(@PathVariable int id, @RequestBody User user) throws RuntimeException, SQLException {
        // rep.deleteAllByIdInBatch(Iterable<ID> ids)
        UserRep.deleteUser(user);
    }

    // @PostMapping("/users/{id}")
    // public Boolean updateUser(@RequestBody String password, @RequestBody String
    // userName, @RequestBody User user) throws RuntimeException, SQLException {

    // User updatedUser = rep.updateUser(user, password, userName);

    // if (user.getUsername() != updatedUser.getUsername()) {
    // return false;
    // }
    // if (user.getPassword() != updatedUser.getPassword()) {
    // return false;
    // }
    // return true;
    // }

    // public void updateUser(){
    // User user = rep.loadUser(email).orElseThrow(() -> new
    // ResourceNotFoundException("User not exist with id: " + id));

    @GetMapping("/users/{id}")
    public User getUserById(@RequestBody User user) throws RuntimeException, SQLException {
        return UserRep.loadUser(user.getEmail());

        // return new User("test@test.no", "123", "Jarl");
    }

    // Travel Routes/ Itinerary

    @CrossOrigin(origins = "*")
    @PostMapping("/itinerary")
    public void addItinerary(@RequestBody Itinerary itinerary) throws SQLException, RuntimeException {
        UserRep.addItinerary(itinerary);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/itinerary/{id}")
    public Itinerary getItinerary(@PathVariable int id) {
        try {
            return UserRep.getItinerary(id);
        } catch (Exception e) {
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/deleteItinerary")
    public void deleteItinerary(@PathVariable int id, @RequestBody Itinerary itinerary)
            throws RuntimeException, SQLException {
        UserRep.deleteItinerary(itinerary);
    }

    @GetMapping("/itineraries/{userEmail}")
    public List<Itinerary> getItinerariesByUserEmail(@PathVariable String userEmail)
            throws RuntimeException, SQLException {
        // List<Itinerary> arrayList = new ArrayList<>();
        // arrayList.add(rep.getItineraryByUserEmail(userEmail));
        // return arrayList;
        return UserRep.getItinerariesByUserEmail(userEmail);
    }

    @GetMapping("/itineraries/{id}")
    public ItineraryDestinationJoined GetItineraryDestiationJoined(@PathVariable int itineraryID) {
        return UserRep.GetItineraryDestiationJoined(itineraryID);
    }

    @PostMapping("/itineraries")
    public void addItineraryDesitationsJoined(@RequestBody List<ItineraryDestinationJoined> destination) {
    }

}
