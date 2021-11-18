package com.rest.RestaurantApp.controllers;

import com.rest.RestaurantApp.domain.Employee;
import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.dto.JwtAuthenticationRequest;
import com.rest.RestaurantApp.dto.UserTokenState;
import com.rest.RestaurantApp.services.EmployeeService;
import com.rest.RestaurantApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;


@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        String jwt = tokenUtils.generateToken(user.getUsername());
        System.out.println(new UserTokenState(jwt));
        return ResponseEntity.ok(new UserTokenState(jwt));
    }

    @GetMapping("login/{pin}")
    public ResponseEntity<EmployeeDTO> loginPin(@PathVariable("pin") int pin) {
        EmployeeDTO employee = employeeService.getOneByPin(pin);

        if(employee == null)
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
