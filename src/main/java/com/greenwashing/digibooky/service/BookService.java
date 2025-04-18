package com.greenwashing.digibooky.service;

import com.greenwashing.digibooky.domain.Book;
import com.greenwashing.digibooky.infrastructure.AuthorRepository;
import com.greenwashing.digibooky.infrastructure.BookRepository;
import com.greenwashing.digibooky.service.DTOs.BookEnhancedDTO;
import com.greenwashing.digibooky.service.DTOs.BookInputDTO;
import com.greenwashing.digibooky.service.DTOs.BookOutputDTO;
import com.greenwashing.digibooky.service.mappers.AuthorMapper;
import com.greenwashing.digibooky.service.mappers.BookMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    // FIELDS
    private BookRepository repository;
    private BookMapper mapper;
    private AuthorMapper authorMapper;
    private AuthorRepository authorRepository;

    // CONSTRUCTOR
    public BookService(BookRepository repository, BookMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // METHODS
    // get all books
    public List<BookOutputDTO> getAll() {
        return repository.getAll().stream()
                .map(mapper::bookToOutputDTO)
                .toList();
    }

    // get book by ID
    public BookOutputDTO getById(long id) {
        Book book = repository.getById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return mapper.bookToOutputDTO(book);
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
        Book book = mapper.inputDTOToBook(dto);
        repository.save(book);
        return mapper.bookToOutputDTO(book);
    }
    // update a book
    public BookOutputDTO update(BookEnhancedDTO dto, long id) {
        Book book = repository.getById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setId(dto.getId());
        book.setAuthor(authorRepository.getById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Author not found")));
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setIsbn(dto.getIsbn());
        book.setRented(dto.isRented());

        return mapper.bookToOutputDTO(book);
    }

    // delete a book
    public void delete(long id) {
        if (!repository.delete(id)) {;
            throw new RuntimeException("Book not found");
        }
    }

}
