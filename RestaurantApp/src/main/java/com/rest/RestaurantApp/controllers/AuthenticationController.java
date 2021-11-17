package com.rest.RestaurantApp.controllers;

import com.rest.RestaurantApp.domain.PrivilegedUser;
import com.rest.RestaurantApp.dto.JwtAuthenticationRequest;
import com.rest.RestaurantApp.dto.UserTokenState;
import com.rest.RestaurantApp.services.UserAuthService;
import com.rest.RestaurantApp.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;


@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/privileged_login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {
        System.out.println(authenticationRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        System.out.println("2");

        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("3");

        System.out.println(authentication.getPrincipal().toString());
        User user = (User) authentication.getPrincipal();
        System.out.println("4");

        String jwt = tokenUtils.generateToken(user.getUsername(), "PRIVILEGED_USER");
        System.out.println("5");
        return ResponseEntity.ok(new UserTokenState(jwt));

    }
}
