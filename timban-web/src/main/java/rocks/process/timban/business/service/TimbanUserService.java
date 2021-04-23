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
import rocks.process.timban.data.domain.TimbanUser;
import rocks.process.timban.data.repository.TimbanUserRepository;

import javax.validation.Valid;
import javax.validation.Validator;

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

    public TimbanUser getCurrentTimbanUser() {
        String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return timbanUserRepository.findByEmail(userEmail);
    }
}
