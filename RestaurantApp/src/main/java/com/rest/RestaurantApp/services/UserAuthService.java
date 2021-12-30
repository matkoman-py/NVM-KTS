package com.rest.RestaurantApp.services;

import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.EmployeeLoginInfo;
import com.rest.RestaurantApp.domain.PrivilegedUser;
import com.rest.RestaurantApp.domain.Role;
import com.rest.RestaurantApp.repositories.EmployeeLoginInfoRepository;
import com.rest.RestaurantApp.repositories.PriviligedUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;


@Service
@Transactional
public class UserAuthService implements UserDetailsService {

    private final PriviligedUserRepository privilegedUserRepository;

    private final EmployeeLoginInfoRepository employeeLoginInfoRepository;

    public UserAuthService(PriviligedUserRepository privilegedUserRepository,
                           EmployeeLoginInfoRepository employeeLoginInfoRepository) {
        this.privilegedUserRepository = privilegedUserRepository;
        this.employeeLoginInfoRepository = employeeLoginInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<PrivilegedUser> user;

        user = privilegedUserRepository.findByUsername(username);
        if(user.isPresent()) {
            return new User(username, user.get().getPassword(), user.get().getAuthorities());
        }

        EmployeeLoginInfo employee;
        System.out.println("hehehe");
        employee = employeeLoginInfoRepository.findByType(username);

        if(employee != null) {
            return new User(employee.getType(), employee.getPincode(), employee.getAuthorities());
        }

        throw new UsernameNotFoundException("Could not find user");
    }
}
