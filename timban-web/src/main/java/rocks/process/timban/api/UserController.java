package rocks.process.timban.api;

import rocks.process.timban.tools.LogToFile;
import rocks.process.timban.data.domain.TimbanUser;
import org.springframework.http.ResponseEntity;
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
    public ArrayList<TimbanUser> getUsers() {
        ArrayList<TimbanUser> users = new ArrayList<>();
        users.add(new TimbanUser("Hans", "hans@example.com", "password", false, 80));
        users.add(new TimbanUser("Jakob", "jakob@example.com", "password", true, 1));
        users.add(new TimbanUser("Lisa", "lisa@example.com", "password", false, 800));
        users.add(new TimbanUser("Jessica", "jessica@example.com", "password", false, 24));
        users.add(new TimbanUser("Ruedi", "ruedi@example.com", "password", false, 41));
        return users;
    }

    /**
     * Get single User based on the id
     */
    @GetMapping(path = "/{id}")
    public TimbanUser getSingleUser() {
        // ToDo: Add business service to retrieve user
        return new TimbanUser("Sven", "sven@example.com", "password", true, 30);
    }

    /**
     * Create new User
     */
    @PostMapping
    public ResponseEntity<TimbanUser> createUser(@RequestBody TimbanUser user) {
        // ToDo: Add business service to save user

        LogToFile.logUser("User created; UserID: " + user.getId() + "; Username: " + user.getUserName());
        return ResponseEntity.ok().body(user);
    }

    /**
     * Update User
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<TimbanUser> updateUser(@RequestBody TimbanUser user) {
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
