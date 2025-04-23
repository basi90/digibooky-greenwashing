package com.greenwashing.digibooky.webapi;

import com.greenwashing.digibooky.domain.Book;
import com.greenwashing.digibooky.service.BookService;
import com.greenwashing.digibooky.service.DTOs.BookEnhancedDTO;
import com.greenwashing.digibooky.service.DTOs.BookInputDTO;
import com.greenwashing.digibooky.service.DTOs.BookOutputDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    // FIELDS
    private final BookService service;

    // CONSTRUCTOR
    public BookController(BookService service) {
        this.service = service;
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
    public BookEnhancedDTO getBookByIdEnhanced(@PathVariable long id) {
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
    public BookOutputDTO createBook(@RequestBody BookInputDTO book) {
        return service.save(book);
    }

    @PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public BookOutputDTO updateBook(@PathVariable long id, @RequestBody BookInputDTO book) {
        return service.update(book, id);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable long id) {
        if (!service.delete(id)) {;
            throw new RuntimeException("Book not found");
        }
    }
}