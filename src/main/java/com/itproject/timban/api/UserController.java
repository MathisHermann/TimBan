package com.itproject.timban.api;

import com.itproject.timban.models.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Author: Mathis
 * Peer: Sven
 * Reviewer: -
 * Date: 21.04.2021
 */

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    @GetMapping
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("Hans", "hans@example.com", "password", false, 80));
        users.add(new User("Jakob", "jakob@example.com", "password", true, 1));
        users.add(new User("Lisa", "lisa@example.com", "password", false, 800));
        users.add(new User("Jessica", "jessica@example.com", "password", false, 24));
        users.add(new User("Ruedi", "ruedi@example.com", "password", false, 41));
        return users;
    }

    @GetMapping(path = "/{id}")
    public User getSingleUser() {
        return new User("Sven", "sven@example.com", "password", true, 30);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Add business service to save user
        return ResponseEntity.ok().body(user);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        // Add business service to save user
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser() {
        // Add business service to delete user
        return ResponseEntity.ok().build();
    }


}
