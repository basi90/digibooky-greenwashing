package com.greenwashing.digibooky.service;

import com.greenwashing.digibooky.domain.Book;
import com.greenwashing.digibooky.domain.Rental;
import com.greenwashing.digibooky.infrastructure.AuthorRepository;
import com.greenwashing.digibooky.infrastructure.BookRepository;
import com.greenwashing.digibooky.infrastructure.RentalRepository;
import com.greenwashing.digibooky.service.DTOs.BookEnhancedDTO;
import com.greenwashing.digibooky.service.DTOs.BookInputDTO;
import com.greenwashing.digibooky.service.DTOs.BookOutputDTO;
import com.greenwashing.digibooky.service.DTOs.UserOutputDTO;
import com.greenwashing.digibooky.service.mappers.AuthorMapper;
import com.greenwashing.digibooky.service.mappers.BookMapper;
import com.greenwashing.digibooky.service.mappers.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class BookService {

    // FIELDS
    private BookRepository repository;
    private BookMapper mapper;
    private AuthorMapper authorMapper;
    private AuthorRepository authorRepository;
    private RentalRepository rentalRepository;
    private UserMapper userMapper;

    // CONSTRUCTOR
    public BookService(BookRepository repository, BookMapper mapper, AuthorRepository authorRepository,
                       AuthorMapper authorMapper, RentalRepository rentalRepository, UserMapper userMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.authorMapper = authorMapper;
        this.authorRepository = authorRepository;
        this.rentalRepository = rentalRepository;
        this.userMapper = userMapper;
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
    public BookEnhancedDTO getByIdEnhanced(long id) {
        Book book = repository.getById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        BookEnhancedDTO dto = new BookEnhancedDTO(
                book.getId(),
                book.getTitle(),
                authorMapper.authorToOutputDTO(book.getAuthor()),
                book.getDescription(),
                book.getIsbn(),
                book.isRented(),
                getRenter(book)
        );

        return dto;
    }

    private UserOutputDTO getRenter(Book book) {
        if (book.isRented()) {
            Rental rental = rentalRepository.getAll().stream()
                    .filter(r -> r.getBook().getId() == book.getId())
                    .findFirst()
                    .orElse(null);
            return rental != null ? userMapper.userToOutputDTO(rental.getUser()) : null;
        }
        return null;
    }

    // Search Books
    public List<BookOutputDTO> getBySearch(String title, String isbn, String firstName, String lastName) {
        // prepare the search strings
        String cleanedTitle = title != null ? title.trim() : null;
        String cleanedIsbn = isbn != null ? isbn.trim() : null;
        String cleanedFirstName = firstName != null ? firstName.trim() : null;
        String cleanedLastName = lastName != null ? lastName.trim() : null;
        // turn them into regex patterns
        Pattern titlePattern = wildcardToPattern(cleanedTitle);
        Pattern isbnPattern = wildcardToPattern(cleanedIsbn);
        Pattern firstNamePattern = wildcardToPattern(cleanedFirstName);
        Pattern lastNamePattern = wildcardToPattern(cleanedLastName);

        return repository.getAll().stream()
                .filter(book -> matches(titlePattern, book.getTitle()))
                .filter(book -> matches(isbnPattern, book.getIsbn()))
                .filter(book -> book.getAuthor() != null
                        && matches(firstNamePattern, book.getAuthor().getFirstName()))
                .filter(book -> book.getAuthor() != null
                        && matches(lastNamePattern, book.getAuthor().getLastName()))
                .map(mapper::bookToOutputDTO)
                .collect(Collectors.toList());
    }


    private Pattern wildcardToPattern(String input) {
        if (input == null || input.isEmpty()) return null;
        String regex = input
                .replace(".", "\\.")
                .replace("*", ".*")
                .replace("?", ".");
        return Pattern.compile("^" + regex + "$", Pattern.CASE_INSENSITIVE);
    }

    private boolean matches(Pattern pattern, String value) {
        return pattern == null || (value != null && pattern.matcher(value).matches());
    }

    /*
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
    */

    // add new book
    public BookOutputDTO save(BookInputDTO dto) {
        Book book = mapper.inputDTOToBook(dto);
        repository.save(book);
        return mapper.bookToOutputDTO(book);
    }
    // update a book
    public BookOutputDTO update(BookInputDTO dto, long id) {
        Book book = repository.getById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setAuthor(authorRepository.getById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found")));
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());

        return mapper.bookToOutputDTO(book);
    }

    // delete a book
    public boolean delete(long id) {
        if (!repository.delete(id)) {;
            return false;
        }
        return true;
    }

}
