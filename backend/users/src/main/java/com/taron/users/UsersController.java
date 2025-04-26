package com.taron.users;

import com.taron.users.models.User;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {

    private UsersService service;

    public UsersController(UsersService service){
        this.service = service;
    }

    @PostMapping()
    public User createOne(@RequestBody User user){
        return this.service.createOne(user);
    }
    
    @GetMapping("/existsByPhone")
    public boolean existsByPhoneNumber(@RequestParam  String phone){
        return this.service.existsByPhoneNumber(phone);
    }
    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable int id){
        return this.service.findById(id);
    }

    @GetMapping("/getAll")
    public List<User> getAll(){
        return this.service.getAll();
    }

    @GetMapping("/findByEmail")
    public Optional<User> findByEmail(@RequestParam String email){
       return this.service.findByEmail(email);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable int id) {
        service.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

}
