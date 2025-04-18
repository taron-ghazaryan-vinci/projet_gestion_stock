package com.taron.users;

import com.taron.users.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    public Optional<User> findOne(@RequestParam String email){
       return this.service.findOne(email);
    }
}
