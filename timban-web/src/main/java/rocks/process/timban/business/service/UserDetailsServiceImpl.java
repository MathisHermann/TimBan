/*
 * Copyright (c) 2019. University of Applied Sciences and Arts Northwestern Switzerland FHNW.
 * All rights reserved.
 */

package rocks.process.timban.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rocks.process.timban.data.domain.TimbanUser;
import rocks.process.timban.data.repository.TimbanUserRepository;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private TimbanUserRepository timbanUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TimbanUser timbanUser = timbanUserRepository.findByEmail(username);
        if (timbanUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(timbanUser.getEmail(), timbanUser.getPassword(), emptyList());
    }
}
