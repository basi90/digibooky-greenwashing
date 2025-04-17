package com.greenwashing.digibooky.service;

import com.greenwashing.digibooky.infrastructure.BookRepository;
import com.greenwashing.digibooky.service.DTOs.BookInputDTO;
import com.greenwashing.digibooky.service.DTOs.BookOutputDTO;
import com.greenwashing.digibooky.service.mappers.BookMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    // FIELDS
    private BookRepository repository;
    private BookMapper mapper;

    // CONSTRUCTOR
    public BookService(BookRepository repository, BookMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // METHODS
    // get all books
    public List<BookOutputDTO> getAll() {
        // get all books from the repository
        // map it to a list of dto
        // return the list of dto
        return null;
    }

    // get book by ID
    public BookOutputDTO getById(long id) {
        // get the book matching the id from repo
            // if not present, handle error with custom exception
        // map the book to a dto
        // return the dto
        return null;
    }

    // get book by ID (enhanced)
    public BookOutputDTO getByIdEnhanced(long id) {
        // get the book matching the id from repo
            // if not present, handle error with custom exception
        // map the book to an enhanced dto
            // if it's rented, associate the user dto
            // if not, leave the field empty
        // return the dto
        return null;
    }
    // get book by ISBN (search)
    public List<BookOutputDTO> getByISBN(String isbn) {
        // parse the search string to a regex
        // make a list of every book that matches it
        // map it to a list of dto
        // return the list of dto
        return null;
    }
    // get book by Title (search)
    public List<BookOutputDTO> getByTitle(String title) {
        // parse the search string to a regex
        // make a list of every book that matches it
        // map it to a list of dto
        // return the list of dto
        return null;
    }
    // get book by Author (search)
    public List<BookOutputDTO> getByAuthor(String author) {
        // parse the search string to a regex
            // can be firstname, lastname or both -> how to handle that?
        // make a list of every book that matches it
        // map it to a list of dto
        // return the list of dto
        return null;
    }
    // add new book
    public BookOutputDTO save(BookInputDTO dto) {
        // map the input dto to an actual book
        // save it in the repository
        // map the saved book to an output dto
        // return the dto
        return null;
    }
    // update a book
    public BookOutputDTO update(BookInputDTO dto) {
        // map the input dto to an actual book
        // save it in the repository
        // map the saved book to an output dto
        // return the dto
        return null;
    }
    // delete a book
    public void delete(long id) {
        // delete the matching book from repo with id
            // if not present, handle error with custom exception
            // keep in mind the repo will actually only transfer it to the
            // "deleted" list (soft-delete)
    }

}
