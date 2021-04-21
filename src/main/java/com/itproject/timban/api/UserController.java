package com.itproject.timban.api;

import com.itproject.timban.config.LogToFile;
import com.itproject.timban.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Author: Mathis
 * Peer: Sven
 * Reviewer: -
 * Date: 21.04.2021
 *
 * This class handles all the requests regarding the model User. Supporting all CRUD operations.
 */

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    /**
     * Get all users available
     */
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

    /**
     * Get single User based on the id
     */
    @GetMapping(path = "/{id}")
    public User getSingleUser() {
        // ToDo: Add business service to retrieve user
        return new User("Sven", "sven@example.com", "password", true, 30);
    }

    /**
     * Create new User
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // ToDo: Add business service to save user

        LogToFile.logUser("User created; UserID: " + user.getId() + "; Username: " + user.getUserName());
        return ResponseEntity.ok().body(user);
    }

    /**
     * Update User
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        // ToDo: Add business service to save user

        LogToFile.logUser("User updated; UserID: " + user.getId() + "; Username: " + user.getUserName());
        return ResponseEntity.ok().body(user);
    }

    /**
     * Delete User
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        // ToDo: Add business service to delete user

        LogToFile.logUser("User deleted; UserID: " + id);
        return ResponseEntity.ok().build();
    }


}
