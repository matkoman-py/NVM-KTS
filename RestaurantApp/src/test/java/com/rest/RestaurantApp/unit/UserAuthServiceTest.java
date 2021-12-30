package com.rest.RestaurantApp.unit;

import com.rest.RestaurantApp.domain.PrivilegedUser;
import com.rest.RestaurantApp.domain.enums.PrivilegedUserType;
import com.rest.RestaurantApp.domain.enums.UserType;
import com.rest.RestaurantApp.repositories.PriviligedUserRepository;
import com.rest.RestaurantApp.services.UserAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class UserAuthServiceTest {

    @MockBean
    private PriviligedUserRepository priviligedUserRepository;

    @Autowired
    private UserAuthService userAuthService;

    @BeforeEach
    public void setup() {
        PrivilegedUser privilegedUser = new PrivilegedUser("marko@gmail.com", "Marko", "Markovic", new Date(),
                UserType.PRIVILEGED_USER, "marko1", "123", PrivilegedUserType.MANAGER);
        privilegedUser.setRoles(new ArrayList<>());

        given(priviligedUserRepository.findByUsername("marko1")).willReturn(Optional.of(privilegedUser));
    }

    @Test
    public void loadUserByUsername_ValidUsername() {
        UserDetails result = userAuthService.loadUserByUsername("marko1");

        assertEquals(result.getUsername(), "marko1");
        assertEquals(result.getPassword(), "123");
    }

    @Test
    public void loadUserByUsername_InvalidUsername() {
        assertThrows(UsernameNotFoundException.class, () -> {
            UserDetails userDetails = userAuthService.loadUserByUsername("invalidUsername");
        });
    }
}
