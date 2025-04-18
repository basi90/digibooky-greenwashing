package com.greenwashing.digibooky.service;

import com.greenwashing.digibooky.domain.User;
import com.greenwashing.digibooky.infrastructure.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String encode (String email, String password) {
        String valueToEncode = email + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    public String[] decode(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }

        String base64 = authHeader.substring(6);
        String decoded = new String(Base64.getDecoder().decode(base64));

        String[] decodedArray = decoded.split(":", 2);

        if (decodedArray.length != 2) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization format");
        }

        return decodedArray;
    }

    public User authenticate(String authHeader) {
        String[] decodedArray = decode(authHeader);
        String email = decodedArray[0];
        String password = decodedArray[1];

        User user = userRepository.getByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }
        return user;
    }
}
