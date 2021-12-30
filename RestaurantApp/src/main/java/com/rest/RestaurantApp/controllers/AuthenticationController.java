package com.rest.RestaurantApp.controllers;

import com.rest.RestaurantApp.dto.EmployeeAuthDTO;
import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.dto.JwtAuthenticationRequest;
import com.rest.RestaurantApp.dto.UserTokenState;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.services.EmployeeService;
import com.rest.RestaurantApp.util.TokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final TokenUtils tokenUtils;

    private final AuthenticationManager authenticationManager;

    private final EmployeeService employeeService;

    public AuthenticationController(TokenUtils tokenUtils, AuthenticationManager authenticationManager, EmployeeService employeeService) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.employeeService = employeeService;
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity handleNullEmployeeException(NotFoundException notFoundException) {
        return new ResponseEntity(notFoundException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        String jwt = tokenUtils.generateToken(user.getUsername(), user.getAuthorities());
        return ResponseEntity.ok(new UserTokenState(jwt));
    }

    @GetMapping(value = "login/{pin}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeAuthDTO> loginPin(@PathVariable("pin") int pin) {
        EmployeeAuthDTO employee = employeeService.getOneByPin(pin);

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
