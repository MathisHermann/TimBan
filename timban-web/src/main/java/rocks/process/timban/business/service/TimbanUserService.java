/*
 * Copyright (c) 2020. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.timban.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rocks.process.timban.data.domain.TimbanTimeRecord;
import rocks.process.timban.data.domain.TimbanUser;
import rocks.process.timban.data.repository.TimbanUserRepository;

import javax.validation.Valid;
import javax.validation.Validator;
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

    public void saveTimbanUser(@Valid TimbanUser timbanUser) throws Exception {
        try {

            if (timbanUser.getId() == null) {
                if (timbanUserRepository.findByEmail(timbanUser.getEmail()) != null) {
                    throw new Exception("Email address " + timbanUser.getEmail() + " already assigned.");
                } else {
                    timbanUser.setPassword(passwordEncoder.encode(timbanUser.getPassword()));
                }
            } else if (getCurrentTimbanUser().isAdmin()) {
                timbanUser.setPassword(passwordEncoder.encode(
                        timbanUserRepository.findById(timbanUser.getId()).get().getPassword()));
            } else {
                throw new Exception("Forbidden");
            }

        } catch (NullPointerException ne) {
            throw new Exception("Not logged in.");
        }

        timbanUserRepository.save(timbanUser);
    }

    public List<TimbanUser> getAllTimbanUsers() {
        return timbanUserRepository.findAll();
    }

    public TimbanUser getCurrentTimbanUser() {
        String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return timbanUserRepository.findByEmail(userEmail);
    }

    public Object getUserById(Long id) {
        Optional<TimbanUser> timbanUser;

        try {
            timbanUser = timbanUserRepository.findById(id);
        } catch (Exception e) {
            timbanUser = Optional.empty();
        }
        return timbanUser;
    }

    public void setUserCurrentlyCheckedIn(TimbanTimeRecord timbanTimeRecord) {
        try {
            timbanUserRepository.findById(timbanTimeRecord.getUserId())
                    .get().setCurrentlyCheckedIn(timbanTimeRecord.getStartRecording());
        } catch (Exception e) {}

    }
}
