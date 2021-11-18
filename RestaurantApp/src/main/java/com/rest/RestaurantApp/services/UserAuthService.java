package com.rest.RestaurantApp.services;

import com.rest.RestaurantApp.domain.PrivilegedUser;
import com.rest.RestaurantApp.repositories.EmployeeRepository;
import com.rest.RestaurantApp.repositories.PriviligedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
public class UserAuthService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PriviligedUserRepository privilegedUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PrivilegedUser user;

//        user = employeeRepository.findByPincode(username);
//        if(user != null) return user;

        user = privilegedUserRepository.findByUsername(username);
        if(user != null) {
            return new User(username, user.getPassword(), user.getAuthorities());
        }
        throw new UsernameNotFoundException("Could not find user");
//        return null;
    }
}
