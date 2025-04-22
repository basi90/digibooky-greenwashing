package com.greenwashing.digibooky.service;

import com.greenwashing.digibooky.domain.Author;
import com.greenwashing.digibooky.domain.Book;
import com.greenwashing.digibooky.infrastructure.AuthorRepository;
import com.greenwashing.digibooky.infrastructure.BookRepository;
import com.greenwashing.digibooky.service.DTOs.AuthorInputDTO;
import com.greenwashing.digibooky.service.DTOs.AuthorOutputDTO;
import com.greenwashing.digibooky.service.DTOs.BookInputDTO;
import com.greenwashing.digibooky.service.DTOs.BookOutputDTO;
import com.greenwashing.digibooky.service.mappers.BookMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    void whenGetAllBooks_ThenReturnAllBooks() {
        Author author = new Author("a", "b");
        Book book1 = new Book("a", author, "description", "isbn");
        Book book2 = new Book("b", author, "description", "isbn");

        when(bookRepository.getAll()).thenReturn(List.of(book1, book2));
        when(bookMapper.bookToOutputDTO(book1)).thenReturn(new BookOutputDTO(
            1,
            "a",
                new AuthorOutputDTO(1,"a","b"),
                "description",
                "isbn"
        ));
        when(bookMapper.bookToOutputDTO(book2)).thenReturn(new BookOutputDTO(
                2,
                "b",
                new AuthorOutputDTO(1, "a", "b"),
                "description",
                "isbn"
        ));

        List<BookOutputDTO> result = bookService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("a");
        assertThat(result.get(1).getTitle()).isEqualTo("b");
    }

    // Create custom exception
    @Test
    void givenId_whenGetBookById_ThenReturnDTO() {
        Author author = new Author("a", "b");
        Book book = new Book("a", author, "description", "isbn");
        long id = book.getId();
        BookOutputDTO bookOutputDTO = new BookOutputDTO(
                id,
                "a",
                new AuthorOutputDTO(
                    1,
                    "a",
                    "b"
                    ),
                "description",
                "isbn"
        );

        when(bookRepository.getById(id)).thenReturn(Optional.of(book));
        when(bookMapper.bookToOutputDTO(book)).thenReturn(bookOutputDTO);

        BookOutputDTO result = bookService.getById(id);
        assertThat(result).isEqualTo(bookOutputDTO);
    }

    @Test
    void givenInput_whenSave_thenReturnDTO() {
        BookInputDTO inputDTO = new BookInputDTO("a", 1,"description", "isbn");
        Author author = new Author("a", "b");
        Book book = new Book("a", author, "description", "isbn");
        BookOutputDTO expectedDTO = new BookOutputDTO(
                1,
                "a",
                new AuthorOutputDTO(
                        1,
                        "a",
                        "b"
                ),
                "description",
                "isbn"
        );

        when(bookMapper.inputDTOToBook(inputDTO)).thenReturn(book);
        when(bookMapper.bookToOutputDTO(book)).thenReturn(expectedDTO);

        BookOutputDTO result = bookService.save(inputDTO);

        assertThat(result).isEqualTo(expectedDTO);
        verify(bookRepository).save(book);
    }

    @Test
    void givenValidId_whenDeleteBook_thenBookIsRemovedFromRepository() {
        long bookId = 1L;

        when(bookRepository.delete(bookId)).thenReturn(true);

        boolean result = bookService.delete(bookId);

        assertThat(result).isTrue();
        verify(bookRepository).delete(bookId);
    }

    @Test
    void givenInvalidId_whenDeleteBook_thenReturnFalse() {
        long invalidBookId = -1L;

        when(bookRepository.delete(invalidBookId)).thenReturn(false);

        boolean result = bookService.delete(invalidBookId);

        assertThat(result).isFalse();
        verify(bookRepository).delete(invalidBookId);
    }
}