package com.rest.RestaurantApp.integration;

import com.rest.RestaurantApp.domain.enums.EmployeeType;
import com.rest.RestaurantApp.dto.EmployeeAuthDTO;
import com.rest.RestaurantApp.dto.EmployeeDTO;
import com.rest.RestaurantApp.dto.JwtAuthenticationRequest;
import com.rest.RestaurantApp.dto.UserTokenState;
import com.rest.RestaurantApp.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCreateAuthenticationToken_Valid() {
        JwtAuthenticationRequest request = new JwtAuthenticationRequest();
        request.setUsername("markuza99");
        request.setPassword("petar123");

        ResponseEntity<UserTokenState> response = restTemplate.postForEntity("/api/auth/login", request, UserTokenState.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testCreateAuthenticationToken_InvalidUsername() {
        JwtAuthenticationRequest request = new JwtAuthenticationRequest();
        request.setUsername("invalid.username");
        request.setPassword("petar123");

        ResponseEntity<UserTokenState> response = restTemplate.postForEntity("/api/auth/login", request, UserTokenState.class);

        assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testCreateAuthenticationToken_InvalidPassword() {
        JwtAuthenticationRequest request = new JwtAuthenticationRequest();
        request.setUsername("markuza99");
        request.setPassword("invalidpassword");

        ResponseEntity<UserTokenState> response = restTemplate.postForEntity("/api/auth/login", request, UserTokenState.class);

        assertEquals(response.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testLoginPin_ValidPin() {
        ResponseEntity<EmployeeAuthDTO> response = restTemplate.getForEntity("/api/auth/login/1234", EmployeeAuthDTO.class);

        EmployeeAuthDTO employee = response.getBody();

        assertEquals(employee.getEmployeeType(), EmployeeType.WAITER);
        assertEquals(employee.getEmail(), "mateja99@yahoo.com");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testLoginPin_InvalidPin() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/auth/login/12345", String.class);

        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }
}
