package group61.backpacking;

import java.io.InputStream;
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
    private UserRepository userRep;
    private ItineraryRepository itineraryRep;

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
            return userRep.loadUser(email);
        } catch (Exception e) {
            return null;
        }

    }

    // @CrossOrigin(origins = "*")
    // @GetMapping("/delete")
    // public void deleteUser(User inUser) throws RuntimeException, SQLException {
    // rep.deleteUser(inUser);
    // }

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

    // Suggestions:

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
        userRep.deleteUser(user);
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
        return userRep.loadUser(user.getEmail());

        // return new User("test@test.no", "123", "Jarl");
    }

    // Travel Routes/ Itinerary



    // argumentene må endres
    // @CrossOrigin(origins = "*")
    // @PostMapping("/itinerary")
    // public void saveItinerary(@RequestBody Itinerary itinerary) throws SQLException {
    //     // Does not exist yet:
    //     List<String> destinationList = UserRep.loadDestinationList(itinerary);

    //     User itineraryUser = UserRep.loadUser(itinerary.getWriterEmail());
    //     UserRep.saveItinerary(itineraryUser, itinerary.getEstimatedTime(), itinerary.getDescription(),
    //             itinerary.getImage(), itinerary.getTitle(), destinationList);
    // } // If there's a problem, it's here^: saveItinerary takes in User object,
    //   // itinerary only gets userID

    @CrossOrigin(origins = "*")
    @GetMapping("/itinerary/{id}")
    public Itinerary getItineraryById(@PathVariable int id)
            throws SQLException, RuntimeException {
        //return itineraryRep.loadItineraryByInput(itinerary.getTitle(), itinerary.getWriterEmail());
        return null;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/itineraries/{userEmail}")
    public List<Itinerary> getItinerariesByUserEmail(@PathVariable String userEmail)
        throws RuntimeException, SQLException {
            return itineraryRep.loadItinerariesByUserEmail(userEmail);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/itinerary")
    public void addItinerary(@RequestBody Itinerary itinerary) {

    }

    @CrossOrigin(origins = "*")
    @GetMapping("/itinerarydestinations/{id}")
    public List<ItineraryDestinationsJoined>
         getItineraryDestinationsJoined(@PathVariable int itineraryID) {
            //return UserRep.loadDestinationsOnItinerary(itineraryID);
            return null;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/itinerarydestinations")
    public void addItineraryDestinationsJoined(@RequestBody 
        List<ItineraryDestinationsJoined> itineraryDestinations) {
    }

}
