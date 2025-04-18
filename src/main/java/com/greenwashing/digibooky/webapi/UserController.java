package com.greenwashing.digibooky.webapi;

import com.greenwashing.digibooky.domain.User;
import com.greenwashing.digibooky.domain.UserRole;
import com.greenwashing.digibooky.service.AuthenticationService;
import com.greenwashing.digibooky.service.DTOs.UserInputDTO;
import com.greenwashing.digibooky.service.DTOs.UserOutputDTO;
import com.greenwashing.digibooky.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(summary = "Get all users", security = @SecurityRequirement(name = "basicAuth"))
    public List<UserOutputDTO> getAllUsers(@RequestHeader("Authorization") String authHeader) {
        authenticateAdmin(authHeader);
        return userService.viewMembersAsAdmin();
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutputDTO registerUser(@RequestBody UserInputDTO userDTO, @RequestHeader(value = "Authorization", required = false) String authHeader) {

        UserRole requestedRole = userDTO.getRole();

        return switch (requestedRole) {
            case MEMBER -> registerMember(userDTO);
            case LIBRARIAN -> registerLibrarian(userDTO, authHeader);
            case ADMIN -> registerAdmin(userDTO, authHeader);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role");
        };
    }

    private void authenticateAdmin(String authHeader) {
        User user = authService.authenticate(authHeader);
        if(user.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin privileges required");
        }

    }

    private UserOutputDTO registerMember(UserInputDTO dto) {
        return userService.registerAsMember(dto);
    }

    private UserOutputDTO registerLibrarian(UserInputDTO dto, String authHeader) {
        authenticateAdmin(authHeader);
        return userService.registerLibrarian(dto);
    }

    private UserOutputDTO registerAdmin(UserInputDTO dto, String authHeader) {
        authenticateAdmin(authHeader);
        return userService.registerAdmin(dto);
    }
}
