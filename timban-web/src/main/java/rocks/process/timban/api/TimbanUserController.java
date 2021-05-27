package rocks.process.timban.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import rocks.process.timban.business.service.TimbanUserService;
import rocks.process.timban.data.repository.TimbanUserRepository;
import rocks.process.timban.tools.LogToFile;
import rocks.process.timban.data.domain.TimbanUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocks.process.timban.tools.ReportPDF;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;


/**
 * Author: Mathis / Sven
 * PairProgrammer: Mathis / Sven
 * Reviewer: -
 * Date: 21.04.2021
 * Edit: 27.04.2021, 18.05.2021
 * <p>
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

    @Autowired
    private ReportPDF reportPDF;

    /**
     * Get all users available / Successfully tested with Postman
     */
    @GetMapping
    public List<TimbanUser> getUsers() {
        return timbanUserRepository.findAll();
    }

    /**
     * Get single User based on the id / Successfully tested with Postman
     *
     * @return single User if existing
     */
    @GetMapping(path = "/{id}")
    public Optional<TimbanUser> getSingleUser(@PathVariable Long id) {
        return timbanUserRepository.findById(id);
    }

    /**
     * Get PDF Report of a user
     *
     * @return url to the report
     */
    @GetMapping(path = "/{id}/report")
    public String getUserReport(@PathVariable Long id) {
        try {
            if (timbanUserService.getUserById(id).isPresent())
                return reportPDF.reportToPDF(timbanUserService.getUserById(id).get());
            else
                return null;
        } catch (Exception e) {
            return null;
        }
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
     * Update User
     */
    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody TimbanUser timbanUser) {
        try {
            timbanUser.setId(timbanUserService.getCurrentTimbanUser().getId());
            timbanUser.setChangedOn(Instant.now());
            timbanUserService.saveTimbanUser(timbanUser);
            LogToFile.logUser("User updated; UserID: " + timbanUser.getId() + "; Username: " + timbanUser.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update User with the admin overview
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> updateUserById(@RequestBody TimbanUser timbanUser, @PathVariable Long id) {
        try {
            TimbanUser updatedUser = timbanUserService.updateUser(timbanUser, id);
            LogToFile.logUser("User updated; UserID: " + updatedUser.getId() + "; Username: " + updatedUser.getUserName());
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
