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
import rocks.process.timban.data.domain.TimBanUser;
import rocks.process.timban.data.repository.TimBanUserRepository;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private TimBanUserRepository timBanUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TimBanUser timBanUser = timBanUserRepository.findByEmail(username);
        if (timBanUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(timBanUser.getEmail(), timBanUser.getPassword(), emptyList());
    }
}
