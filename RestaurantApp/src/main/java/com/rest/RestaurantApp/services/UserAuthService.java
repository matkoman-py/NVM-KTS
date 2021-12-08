package com.rest.RestaurantApp.services;

import com.rest.RestaurantApp.domain.PrivilegedUser;
import com.rest.RestaurantApp.repositories.PriviligedUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserAuthService implements UserDetailsService {

    private final PriviligedUserRepository privilegedUserRepository;

    public UserAuthService(PriviligedUserRepository privilegedUserRepository) {
        this.privilegedUserRepository = privilegedUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PrivilegedUser user;

        user = privilegedUserRepository.findByUsername(username);
        if(user != null) {
            return new User(username, user.getPassword(), user.getAuthorities());
        }

        throw new UsernameNotFoundException("Could not find user");
    }
}
