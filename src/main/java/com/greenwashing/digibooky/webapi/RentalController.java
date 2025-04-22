package com.greenwashing.digibooky.webapi;

import com.greenwashing.digibooky.domain.User;
import com.greenwashing.digibooky.domain.UserRole;
import com.greenwashing.digibooky.service.AuthenticationService;
import com.greenwashing.digibooky.service.DTOs.RentalInputDTO;
import com.greenwashing.digibooky.service.DTOs.RentalOutputDTO;
import com.greenwashing.digibooky.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController   {

    private final RentalService rentalService;
    private final AuthenticationService authService;
    public RentalController(RentalService rentalService, AuthenticationService authService) {
        this.rentalService = rentalService;
        this.authService = authService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Borrow a book as a member", security = @SecurityRequirement(name = "basicAuth"))
    public RentalOutputDTO borrowBook(@RequestBody RentalInputDTO rentalInputDTO, @RequestHeader("Authorization") String authHeader) {
        authService.authenticate(authHeader);
        return this.rentalService.borrowBook(rentalInputDTO);
    }

    @DeleteMapping(path = "/{rentalId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Return a book as a member", security = @SecurityRequirement(name = "basicAuth"))
    public String returnBook(@PathVariable long rentalId, @RequestHeader("Authorization") String authHeader) {
        authService.authenticate(authHeader);
        return this.rentalService.returnBook(rentalId);// returning a string doesn't really do anything on the website...
    }


    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all current rented books", security = @SecurityRequirement(name = "basicAuth"))
    public List<RentalOutputDTO> getAllRentals(@RequestHeader("Authorization") String authHeader) {
        authenticateLibrarian(authHeader);
        return this.rentalService.getAllRentalsDTO();
    }

    @GetMapping(path = "/{userId}",produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all rentals of a given member", security = @SecurityRequirement(name = "basicAuth"))
    public List<RentalOutputDTO> getAllRentalsFromUser(@PathVariable long userId, @RequestHeader("Authorization") String authHeader) {
        authenticateLibrarian(authHeader);
        return this.rentalService.getAllRentalsFromUser(userId);
    }

    @GetMapping(path = "/overdue", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all rentals that are overdue", security = @SecurityRequirement(name = "basicAuth"))
    public List<RentalOutputDTO> getAllRentalsOverdue( @RequestHeader("Authorization") String authHeader) {
        authenticateLibrarian(authHeader);
        return this.rentalService.getAllRentalsOverdue();
    }

    private void authenticateLibrarian(String authHeader) {// I think it makes more sense to create a public method in authentication service? im not sure
        User user = authService.authenticate(authHeader);
        if(user.getRole() != UserRole.LIBRARIAN || user.getRole() != UserRole.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Librarian or admin privileges required");
        }
    }

}
