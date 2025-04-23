package com.greenwashing.digibooky.webapi;

import com.greenwashing.digibooky.domain.Book;
import com.greenwashing.digibooky.domain.User;
import com.greenwashing.digibooky.domain.UserRole;
import com.greenwashing.digibooky.service.AuthenticationService;
import com.greenwashing.digibooky.service.BookService;
import com.greenwashing.digibooky.service.DTOs.BookEnhancedDTO;
import com.greenwashing.digibooky.service.DTOs.BookInputDTO;
import com.greenwashing.digibooky.service.DTOs.BookOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    // FIELDS
    private final BookService service;
    private final AuthenticationService authService;

    // CONSTRUCTOR
    public BookController(BookService service, AuthenticationService authService) {
        this.service = service;
        this.authService = authService;
    }

    // METHODS
    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookOutputDTO> getAllBooks() {
        return service.getAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public BookOutputDTO getBookById(@PathVariable long id) {
        return service.getById(id);
    }

    @GetMapping(path = "/{id}/enhanced", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get book details", security = @SecurityRequirement(name = "basicAuth"))
    public BookEnhancedDTO getBookByIdEnhanced(@PathVariable long id, @RequestHeader("Authorization") String authHeader) {
        authService.authenticate(authHeader);
        authenticateLibrarian(authHeader);
        return service.getByIdEnhanced(id);
    }


    @GetMapping(path = "/search", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookOutputDTO> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName
    ) {
        return service.getBySearch(title, isbn, firstName, lastName);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create a book", security = @SecurityRequirement(name = "basicAuth"))
    public BookOutputDTO createBook(@RequestBody BookInputDTO book, @RequestHeader("Authorization") String authHeader) {
        authService.authenticate(authHeader);
        authenticateLibrarian(authHeader);
        return service.save(book);
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "update a book", security = @SecurityRequirement(name = "basicAuth"))
    public BookOutputDTO updateBook(@PathVariable long id, @RequestBody BookInputDTO book, @RequestHeader("Authorization") String authHeader) {
        authService.authenticate(authHeader);
        authenticateLibrarian(authHeader);
        return service.update(book, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete a book", security = @SecurityRequirement(name = "basicAuth"))
    public void deleteBook(@PathVariable long id, @RequestHeader("Authorization") String authHeader) {
        authService.authenticate(authHeader);
        authenticateLibrarian(authHeader);
        if (!service.delete(id)) {;
            throw new RuntimeException("Book not found");
        }
    }

    // Check permission group
    private void authenticateLibrarian(String authHeader) {// I think it makes more sense to create a public method in authentication service? im not sure
        User user = authService.authenticate(authHeader);
        if(user.getRole() == UserRole.MEMBER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Librarian or admin privileges required");
        }
    }
}