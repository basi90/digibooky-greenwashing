package com.greenwashing.digibooky.webapi;

import com.greenwashing.digibooky.domain.User;
import com.greenwashing.digibooky.domain.UserRole;
import com.greenwashing.digibooky.service.AuthenticationService;
import com.greenwashing.digibooky.service.DTOs.UserOutputDTO;
import com.greenwashing.digibooky.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authService;

    public UserController(UserService userService, AuthenticationService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<UserOutputDTO> getAllUsers(@RequestHeader("Authorisation") String authHeader) {
        User admin = authenticateAdmin(authHeader);
        return userService.viewMembersAsAdmin();
    }

    private User authenticateAdmin(String authHeader) {
        User user = authService.authenticate(authHeader);
        if(user.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin privileges required");
        }

        return user;
    }
}
