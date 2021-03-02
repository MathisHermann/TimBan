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
import rocks.process.timban.data.domain.TimBanUser;
import rocks.process.timban.data.repository.TimBanUserRepository;

import javax.validation.Valid;
import javax.validation.Validator;

@Service
@Validated
public class TimBanUserService {

    @Autowired
    private TimBanUserRepository timBanUserRepository;
    @Autowired
    Validator validator;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveTimBanUser(@Valid TimBanUser timBanUser) throws Exception {
        if (timBanUser.getId() == null) {
            if (timBanUserRepository.findByEmail(timBanUser.getEmail()) != null) {
                throw new Exception("Email address " + timBanUser.getEmail() + " already assigned another agent.");
            }
        } else if (timBanUserRepository.findByEmailAndIdNot(timBanUser.getEmail(), timBanUser.getId()) != null) {
            throw new Exception("Email address " + timBanUser.getEmail() + " already assigned another agent.");
        }
        timBanUser.setPassword(passwordEncoder.encode(timBanUser.getPassword()));
        timBanUserRepository.save(timBanUser);
    }

    public TimBanUser getCurrentTimBanUser() {
        String userEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return timBanUserRepository.findByEmail(userEmail);
    }
}
