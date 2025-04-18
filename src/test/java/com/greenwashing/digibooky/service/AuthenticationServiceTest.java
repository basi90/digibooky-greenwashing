package com.greenwashing.digibooky.service;

import com.greenwashing.digibooky.domain.User;
import com.greenwashing.digibooky.domain.UserRole;
import com.greenwashing.digibooky.infrastructure.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void givenValidCredentials_whenAuthenticate_thenReturnUser() {

        User mockUser = new User(UserRole.MEMBER, "a", "x@y.z", "c", "Brussels", "password");

        when(userRepository.getByEmail("x@y.z")).thenReturn(mockUser);

        String encoded = authenticationService.encode("x@y.z", "password");

        User result = authenticationService.authenticate(encoded);

        assertNotNull(result);
        assertEquals("x@y.z", result.getEmail());
    }

    @Test
    void givenWrongPassword_whenAuthenticate_thenThrowError() {
        User mockUser = new User(UserRole.MEMBER, "a", "x@y.z", "c", "Brussels", "password");

        when(userRepository.getByEmail("x@y.z")).thenReturn(mockUser);

        String encoded = authenticationService.encode("x@y.z", "wrongPassword");


        assertThrows(ResponseStatusException.class, () -> {
            authenticationService.authenticate(encoded);
        });
    }

    @Test
    void givenWrongUser_whenAuthenticate_thenThrowError() {
        when(userRepository.getByEmail("wrong@email.com")).thenReturn(null);
        String encoded = authenticationService.encode("wrong@email.com", "who needs it?");

        assertThrows(ResponseStatusException.class, () -> {
            authenticationService.authenticate(encoded);
        });
    }

    @Test
    void givenBadHeader_whenAuthenticate_thenThrowError() {
        String badHeader = "NotBasic base64";

        assertThrows(ResponseStatusException.class, () -> {
            authenticationService.authenticate(badHeader);
        });
    }
    
}
