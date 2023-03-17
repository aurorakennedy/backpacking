package group61.backpacking;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
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

    private UserRepository userRep = new UserRepository();
    private ItineraryRepository itineraryRep = new ItineraryRepository();

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

    @CrossOrigin(origins = "*")
    @GetMapping("/usernames/{email}")
    public ResponseEntity<String> getUsernameByEmail(@PathVariable String email) throws RuntimeException, SQLException {
        String username = userRep.loadUser(email).getUsername();
        return ResponseEntity.ok(username);
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
    @GetMapping("/getitineraries/{userEmail}")
    public List<Itinerary> getItinerariesByUserEmail(@PathVariable String userEmail)
        throws RuntimeException, SQLException {
            /* List<Itinerary> itineraries = itineraryRep.loadItinerariesByUserEmail(userEmail);
            return itineraryRep.loadItineraryAndDestinations(itineraries); */
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
    @GetMapping("/itineraryanddestinations/{id}")
    public ItineraryAndDestinations getItineraryDestinations(@PathVariable int id) throws RuntimeException, SQLException {
        Itinerary itinerary = itineraryRep.loadItineraryById(id);
        List<Itinerary> list = new ArrayList<>();
        list.add(itinerary);
        List<ItineraryAndDestinations> outputList = itineraryRep.loadItineraryAndDestinations(list);
        System.out.println(outputList.get(0).getDestinations());
        return outputList.get(0);
            
    }

    @GetMapping("/itineraries") 
    public List<Itinerary> search(@PathVariable String keyword) throws SQLException {
            return itineraryRep.searchByKeyword(keyword);
    }



      @CrossOrigin(origins = "*")
      @DeleteMapping("/deleteitinerary/{itineraryId}")
      public void deleteItinerary(@PathVariable int itineraryId) throws SQLException {
          itineraryRep.deleteItinerary(itineraryId);
      } 
  
    
    @CrossOrigin(origins = "*")
    @PostMapping("/additineraryanddestinationswithimage")
    public void addItineraryAndDestinations(@RequestBody 
    ItineraryAndDestinationsWithImage itineraryAndDestinationsWithImage) throws SQLException, IOException {
        System.out.println("HTTP request received in controller");
        System.out.println("IMAGE IN CONTROLLER: " + itineraryAndDestinationsWithImage.getImageByteArray());
        itineraryRep.saveItinerary(itineraryAndDestinationsWithImage);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/likeditineraries/{id}")
    public List<Itinerary>
         getItineraryDestinations(@PathVariable User user) throws SQLException {
            return itineraryRep.loadLikedItineraries(user.getEmail());
    }

    @CrossOrigin(origins = "*")
    @PatchMapping("/likes/{email}/{itineraryId}")
    public void updateLikeOnItinerary(@PathVariable String email, 
        @PathVariable int itineraryId) throws SQLException {
            itineraryRep.updateLikedItinerary(email, itineraryId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/likes/{email}/{itineraryId}")
    public boolean itineraryIsLiked(@PathVariable String email, 
        @PathVariable int itineraryId) throws SQLException {
            return itineraryRep.likedItinerary(email, itineraryId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getlikeditineraries/{email}")
    public List<Itinerary> getLikedItineraries(@PathVariable String email) throws SQLException {
        return itineraryRep.loadLikedItineraries(email);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getrecommendeditineraries/{email}")
    public List<Itinerary> getRecommendedItineraries(@PathVariable String email) throws SQLException {
        return itineraryRep.getRecommendedItineraries(email);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/averageratingofitinerary/{itineraryId}")
    public double getItineraryAverageRating(@PathVariable int itineraryId) throws SQLException {
        return itineraryRep.getItineraryAverageRating(itineraryId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getuserratingofitinerary/{userEmail}/{itineraryId}")
    public double getUserRatingOnItinerary(@PathVariable String userEmail, @PathVariable int itineraryId) throws SQLException {
        return itineraryRep.getUserRatingOnItinerary(userEmail, itineraryId);
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/addratingofitinerary/{userEmail}/{itineraryId}")
    public void addRating(@PathVariable String userEmail, @PathVariable int itineraryId,  @RequestBody int rating) throws SQLException {
        if (rating == 0) {
            itineraryRep.deleteRating(userEmail, itineraryId);
        } else {
            itineraryRep.saveRating(userEmail, itineraryId, rating);
        }
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/updateitinerary")
    public void updateItinerary(@RequestBody Itinerary itinerary) throws SQLException {
            itineraryRep.updateItinerary(itinerary);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getrateditineraries/{userEmail}")
    public List<Itinerary> getRatedItineraries(@PathVariable String userEmail) throws SQLException {
        return itineraryRep.loadRatedItineraries(userEmail);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getitineraryimage/{itineraryId}")
    public byte[] getItineraryImage(@PathVariable int itineraryId) throws SQLException {
        return itineraryRep.loadItineraryImage(itineraryId);
    }

}
