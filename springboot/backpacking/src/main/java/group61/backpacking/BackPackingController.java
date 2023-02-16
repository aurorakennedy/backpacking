package group61.backpacking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/save")
    public List<User> saveUser(User inUser){
        return null;
        //return rep.saveUser(inUser);
        //return rep.findAll(User);

    }

    

    
    @GetMapping("/load")
    public List<User> loadUsers(){
        return null;
        //return rep.loadUsers();
        

    }
    

    @GetMapping("/delete")
    public void deleteUser(User inUser){
        //rep.deleteUser(inUser);

    }


    // Suggestions:

    @PostMapping("/register")
    public void register(@RequestBody User user) {
        //User savedUser = rep.save(user);  
    }

    @PostMapping("/login")
    public boolean login(@RequestBody String[] request) {
        boolean loggedIn = false;
        return loggedIn;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        //rep.deleteAllByIdInBatch(Iterable<ID> ids)
    }

    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User user) {

    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        //return rep.findOne(id); 
        return null;
    }


    
}
