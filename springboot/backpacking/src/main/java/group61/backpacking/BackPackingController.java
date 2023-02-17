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
    @PostMapping("/save")
    public User saveUser(User inUser){
        try {
            return rep.saveUser(inUser);

        } catch (Exception e) {
            return null;
        }


    }

    @CrossOrigin(origins = "*")
    @GetMapping("/load")
    public User loadUser(String email)  {
        try {
            return rep.loadUser(email);
        } catch (Exception e) {
            return null;
        }
        

    }
    
    @CrossOrigin(origins = "*")
    @GetMapping("/delete")
    public void deleteUser(User inUser){
        rep.deleteUser(inUser);
    }


    @PostMapping("/login")
    public User login(@RequestBody User user) {
        
        try {
            return rep.login(user);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }


    // Suggestions:

    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public void register(@RequestBody User user) {
        // User savedUser = rep.save(user);
        System.out.println("Register http recieved");
        System.out.println(user.getUserName());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
    }

   

    @CrossOrigin(origins = "*")
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        //rep.deleteAllByIdInBatch(Iterable<ID> ids)
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User user) {

    }

    @CrossOrigin(origins = "*")
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        // return rep.findOne(id);
        return new User("test@test.no", "123", "Jarl");
    }


    
}
