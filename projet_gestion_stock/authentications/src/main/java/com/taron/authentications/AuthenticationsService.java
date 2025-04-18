package com.taron.authentications;

import com.taron.authentications.models.User;
import com.taron.authentications.repositories.UsersProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationsService {

    private final UsersProxy usersProxy;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public AuthenticationsService(UsersProxy usersProxy){
        this.usersProxy = usersProxy;
    }


    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersProxy.createOne(user);
    }

    public boolean existsByEmail(String email){
        return this.usersProxy.findOne(email).isPresent();
    }

    public boolean existsByPhoneNumber(String phone){
        return this.usersProxy.existsByPhoneNumber(phone);
    }

    public User login(String email, String rawPassword) {
        User user = usersProxy.findOne(email)
                .orElseThrow(() -> new RuntimeException("Email non trouvé"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        return user;
    }


}
