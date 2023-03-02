package group61.backpacking;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackPackingController {

    @Autowired
    private UserRepository userRep;

    @Autowired
    private ItineraryRepository itineraryRep;

    @CrossOrigin(origins = "*")
    @GetMapping("/load")
    public User loadUser(String email) {
        try {
            return userRep.loadUser(email);
        } catch (Exception e) {
            return null;
        }

    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        System.out.println("Login request recieved on backend.");
        try {
            return userRep.login(user);
        } catch (Exception e) {
            System.out.println(e.toString());
            // TODO: handle exception
            return null;
        }
    }

    // Create a new user
    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public User register(@RequestBody User user) throws SQLException, RuntimeException {
        try {

            User savedUser = userRep.saveUser(user);
            
            return savedUser;
        } catch (Exception e) {
            System.out.println(e.toString());
            // TODO: handle exception
            return null;
        }
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User user) {

    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id, @RequestBody User user) throws RuntimeException, SQLException {
        userRep.deleteUser(user);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/users/{id}")
    public User getUserById(@RequestBody User user) throws RuntimeException, SQLException {
        return userRep.loadUser(user.getEmail());
    }

    // Travel Routes/ Itinerary

    @CrossOrigin(origins = "*")
    @GetMapping("/itinerary/{id}")
    public Itinerary getItineraryById(@PathVariable int id)
            throws SQLException, RuntimeException {
        // return itineraryRep.loadItineraryByID(id);
        return new Itinerary(id, "test@test.com", "date", 
            100, "desc", "img", "title");
        // return null;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/itineraries/{userEmail}")
    public List<Itinerary> getItinerariesByUserEmail(@PathVariable String userEmail)
        throws RuntimeException, SQLException {
            Itinerary itinerary1 = new Itinerary(0, userEmail, "date", 0, 
                "desc", "img", "itinerary1");
            Itinerary itinerary2 = new Itinerary(0, userEmail, "date", 0, 
                "desc", "img", "itinerary2");
            List<Itinerary> itineraryList = new ArrayList<>();
            itineraryList.add(itinerary1);
            itineraryList.add(itinerary2);
            return itineraryList;
            // return itineraryRep.loadItinerariesByUserEmail(userEmail);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/additinerary")
    public void addItinerary(@RequestBody Itinerary itinerary) {
        // itineraryRep.saveItinerary(itinerary);
        System.out.println(itinerary);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/itinerarydestinations/{id}")
    public List<ItineraryDestination>
         getItineraryDestinations(@PathVariable int itineraryID) {
            // return itineraryRep.loadItineraryDestinations(itineraryID);
            return null;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/additinerarydestinations")
    public void addItineraryDestinations(@RequestBody 
        List<ItineraryDestination> itineraryDestinations) {
            // itineraryRep.saveItineraryDestinations(itineraryDestionations);
            for (ItineraryDestination itineraryDestination : itineraryDestinations) {
                System.out.println(itineraryDestination.toString());
            }
    }

}
