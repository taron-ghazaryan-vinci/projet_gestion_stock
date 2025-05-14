package com.taron.users;

import com.taron.users.models.User;
import com.taron.users.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Optional<User> findByEmail(String email){
        return this.repository.findByEmail(email);
    }

    public Optional<User> findById(int id){

        return this.repository.findById(id);
    }

    public List<User> getAll(){
        return this.repository.findAll();
    }

    public void deleteOne(int id){
        repository.deleteById(id);
    }

    public List<User> getAllByEnterprise(int idEnterprise){
        return this.repository.findByIdEnterprise(idEnterprise);
    }

}
