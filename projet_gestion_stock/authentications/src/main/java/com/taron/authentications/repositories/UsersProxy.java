package com.taron.authentications.repositories;


import com.taron.authentications.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name="users", path ="/users")
public interface UsersProxy {

    @PostMapping()
    User createOne(@RequestBody User user);

    @GetMapping("/existsByPhone")
    boolean existsByPhoneNumber(@RequestParam  String phone);

    @GetMapping()
    Optional<User> findOne(@RequestParam String email);
}
