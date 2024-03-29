/*
 * Copyright (c) 2020. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.timban.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rocks.process.timban.data.domain.TimbanTimeRecord;
import rocks.process.timban.data.domain.TimbanUser;
import rocks.process.timban.data.repository.TimbanUserRepository;

import javax.validation.Valid;
import javax.validation.Validator;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Author:
 * PairProgrammer:
 * Reviewer:
 * Date:
 */

@Service
@Validated
public class TimbanUserService {

    @Autowired
    private TimbanUserRepository timbanUserRepository;

    @Autowired
    Validator validator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Save a user - either Post or Put
     * @param timbanUser - must be valid or exception is thrown
     * @throws Exception - there are some cases where an exception is thrown
     */
    public void saveTimbanUser(@Valid TimbanUser timbanUser) throws Exception {

        if (timbanUser.getId() == null) {
            if (timbanUserRepository.findByEmail(timbanUser.getEmail()) != null) {
                throw new Exception("Email address " + timbanUser.getEmail() + " already assigned another agent.");
            }
        } else if (timbanUserRepository.findByEmailAndIdNot(timbanUser.getEmail(), timbanUser.getId()) != null) {
            throw new Exception("Email address " + timbanUser.getEmail() + " already assigned another agent.");
        }
        timbanUser.setPassword(passwordEncoder.encode(timbanUser.getPassword()));
        timbanUserRepository.save(timbanUser);

    }

    public TimbanUser updateUser(TimbanUser timbanUser, Long id) {
        timbanUser.setId(id);
        timbanUser.setPassword(getUserById(id).get().getPassword());
        timbanUser.setChangedOn(Instant.now());
        return timbanUserRepository.save(timbanUser);
    }

    /**
     * Set the status of the current user to checked-in or checked-out
     * if the user triggers the creation of a new time record.
     * @param timbanTimeRecord - time record that is created upon user trigger
     */
    public void changeCheckedInStatus(TimbanTimeRecord timbanTimeRecord) {
        try {
            TimbanUser timbanUser = getCurrentTimbanUser();
            timbanUser.setCurrentlyCheckedIn(!timbanTimeRecord.getStartRecording());
            saveTimbanUser(timbanUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Return a list of all users
     * @return List
     */
    public List<TimbanUser> getAllTimbanUsers() {
        return timbanUserRepository.findAll();
    }

    /**
     * Get the current user
     * @return TimbanUser
     */
    public TimbanUser getCurrentTimbanUser() {
        String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return timbanUserRepository.findByEmail(userEmail);
    }

    /**
     * Get a user based upon the user id
     * @param id - Long
     * @return - TimbanUser
     */
    public Optional<TimbanUser> getUserById(Long id) {
        Optional<TimbanUser> timbanUser;

        try {
            timbanUser = timbanUserRepository.findById(id);
        } catch (Exception e) {
            timbanUser = Optional.empty();
        }
        return timbanUserRepository.findById(id);
    }

}
