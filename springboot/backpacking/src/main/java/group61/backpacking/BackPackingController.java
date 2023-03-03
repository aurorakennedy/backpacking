package group61.backpacking;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntToDoubleFunction;

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
        return null;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/itineraries/{userEmail}")
    public List<Itinerary> getItinerariesByUserEmail(@PathVariable String userEmail)
        throws RuntimeException, SQLException {
            return itineraryRep.loadItinerariesByUserEmail(userEmail);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/additinerary")
    public void addItinerary(@RequestBody Itinerary itinerary) {
        //itineraryRep.saveItinerary(itinerary);
        System.out.println(itinerary);
    }

    // takes in an id input and returns the itinerary and destinations as a ItineraryAndDestinations object
    @CrossOrigin(origins = "*")
    @GetMapping("/itinerarydestinations/{id}")
    public ItineraryAndDestinations getItineraryDestinations(@PathVariable int itineraryID) throws RuntimeException, SQLException {
        Itinerary itinerary = itineraryRep.loadItineraryById(itineraryID);
        List<Itinerary> list = new ArrayList<>();
        list.add(itinerary);
        List<ItineraryAndDestinations> outputList = itineraryRep.loadItineraryAndDestinations(list);
        return outputList.get(0);
            
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


    @CrossOrigin(origins = "*")
    @PostMapping("/additineraryanddestinations")
    public void addItineraryAndDestinations(@RequestBody 
    ItineraryAndDestinations itineraryAndDestinations) throws SQLException {
        itineraryRep.saveItinerary(itineraryAndDestinations);
    }

}
