package group61.backpacking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackPackingController {

    @Autowired
    private BackPackingRepository rep;

    @PostMapping("/save")
    public List<User> saveUser( User inUser){
        return rep.saveUser(inUser);
    }

    @GetMapping("/load")
    public List<User> loadUsers(){
        return rep.loadUsers();
    }

    @GetMapping("/delete")
    public void deleteUser(User inUser){
        rep.deleteUser(inUser);


    }
    
}
