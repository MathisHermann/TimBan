package rocks.process.timban.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import rocks.process.timban.business.service.TimbanUserService;
import rocks.process.timban.data.repository.TimbanUserRepository;
import rocks.process.timban.tools.LogToFile;
import rocks.process.timban.data.domain.TimbanUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;


/**
 * Author: Mathis / Sven
 * PairProgrammer: Mathis / Sven
 * Reviewer: -
 * Date: 21.04.2021, 27.04.2021
 *
 * This class handles all the requests regarding the model User. Supporting all CRUD operations.
 * Only the Admin is able to access these operations.
 */

@RestController
@RequestMapping(path = "/api/users")
public class TimbanUserController {

    @Autowired
    private TimbanUserRepository timbanUserRepository;

    @Autowired
    private TimbanUserService timbanUserService;

    /**
     * Get all users available / Successfully tested with Postman
     */
    @GetMapping
    public List<TimbanUser> getUsers() {
        return timbanUserRepository.findAll();
    }

    /**
     * Get single User based on the id / Successfully tested with Postman
     * @return
     */
    @GetMapping(path = "/{id}")
    public Optional<TimbanUser> getSingleUser(@PathVariable Long id) {
        return timbanUserRepository.findById(id);
    }

    /**
     * Create new User / Successfully tested with Postman
     */
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody TimbanUser timbanUser) {
        try {
            timbanUser.setCreatedOn(Instant.now());
            timbanUserService.saveTimbanUser(timbanUser);
            LogToFile.logUser("User created; UserID: " + timbanUser.getId() + "; Username: " + timbanUser.getUserName());
        return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update User / TODO Unsuccessfully tested with Postman error400
     */
    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody TimbanUser timbanUser) {
        try {
            timbanUser.setId(timbanUserService.getCurrentTimbanUser().getId());
            timbanUserService.saveTimbanUser(timbanUser);
            LogToFile.logUser("User updated; UserID: " + timbanUser.getId() + "; Username: " + timbanUser.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete User / Successfully tested with Postman
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            timbanUserRepository.deleteById(id);
            LogToFile.logUser("User deleted; UserID: " + id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


}
