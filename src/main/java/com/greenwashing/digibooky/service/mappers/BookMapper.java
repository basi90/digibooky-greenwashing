package com.greenwashing.digibooky.service.mappers;

import com.greenwashing.digibooky.domain.Book;
import com.greenwashing.digibooky.infrastructure.AuthorRepository;
import com.greenwashing.digibooky.service.DTOs.BookInputDTO;
import com.greenwashing.digibooky.service.DTOs.BookOutputDTO;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    // FIELDS
    AuthorMapper authorMapper;
    AuthorRepository authorRepository;

    // CONSTRUCTOR
    public BookMapper(AuthorMapper authorMapper, AuthorRepository authorRepository) {
        this.authorMapper = authorMapper;
        this.authorRepository = authorRepository;
    }

    // METHODS
    public BookOutputDTO bookToBookOutputDTO(Book book) {
        return new BookOutputDTO(
                book.getId(),
                book.getTitle(),
                authorMapper.authorToOutputDTO(book.getAuthor()),
                book.getDescription(),
                book.getIsbn()
        );
    }

    public Book bookInputDTOToBook(BookInputDTO DTO) {
        return new Book(
                DTO.getTitle(),
                authorRepository.getById( DTO.getAuthorId()),
                DTO.getDescription(),
                DTO.getIsbn()
        );
    }
}