package com.taron.users;

import com.taron.users.models.User;
import com.taron.users.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository repository;

    public UsersService(UsersRepository repository){
        this.repository = repository;
    }

    public User createOne(User user){
        return this.repository.save(user);
    }

    public boolean existsByPhoneNumber(String phone){
        return this.repository.existsByPhoneNumber(phone);
    }
    public Optional<User> findOne(String email){
        return this.repository.findByEmail(email);
    }
}
