package com.rest.RestaurantApp.integration;
import static org.junit.jupiter.api.Assertions.*;

import com.rest.RestaurantApp.services.UserAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserAuthServiceTest {

    @Autowired
    private UserAuthService userAuthService;

    @Test
    public void testLoadUserByUsername_validUsername() {
        UserDetails user = userAuthService.loadUserByUsername("markuza99");

        assertEquals(user.getUsername(), "markuza99");
    }

    @Test
    public void testLoadUserByUsername_InvalidUsername() {
    	assertEquals(null, userAuthService.loadUserByUsername("invalid.username"));
    }
}
