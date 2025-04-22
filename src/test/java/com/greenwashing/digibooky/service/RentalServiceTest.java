package com.greenwashing.digibooky.service;


import com.greenwashing.digibooky.domain.*;
import com.greenwashing.digibooky.infrastructure.BookRepository;
import com.greenwashing.digibooky.infrastructure.RentalRepository;
import com.greenwashing.digibooky.infrastructure.UserRepository;
import com.greenwashing.digibooky.service.DTOs.*;
import com.greenwashing.digibooky.service.mappers.RentalMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTest {

    private static final UserRole MEMBER = UserRole.MEMBER;
    User user = new User(MEMBER,"ssn","email","lastname","city","password");
    Author author = new Author("firstName","lastName");
    Book book = new Book("title",author,"description","isbn");

    @Mock
    private RentalMapper rentalMapper;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private RentalService rentalService;

    @Test
    public void whenBorrowBookWIthValidInput_thenReturnRentalOutputDTO() {
        RentalInputDTO payload = new RentalInputDTO(1,1, LocalDate.now().plusWeeks(3));

        UserOutputDTO userDTO = new UserOutputDTO(1,MEMBER,"email","firstname","lastname",
                "streetname",1,"city","password");

        AuthorOutputDTO authorDTO = new AuthorOutputDTO(1,"firstname","lastname");
        BookOutputDTO bookDTO = new BookOutputDTO(1,"title",authorDTO,"description","isbn");

        Rental rental = new Rental(user, book, LocalDate.now().plusWeeks(3));
        RentalOutputDTO rentalDTO = new RentalOutputDTO(1,userDTO,bookDTO,LocalDate.now().plusWeeks(3));

        when(rentalMapper.inputDTOToRental(payload)).thenReturn(rental);
        when(rentalMapper.rentalToOutputDTO(rental)).thenReturn(rentalDTO);
        when(bookRepository.getById(payload.getBookId())).thenReturn(Optional.of(book));

        RentalOutputDTO result = rentalService.borrowBook(payload);

        verify(rentalMapper).inputDTOToRental(payload);
        verify(rentalRepository).save(rental);
        verify(rentalMapper).rentalToOutputDTO(rental);

        assertThat(result).isEqualTo(rentalDTO);
    }

    @Test
    public void whenGetAllRentalsDTO_thenReturnListOfRentalOutputDTO() {
        UserOutputDTO userDTO = new UserOutputDTO(1,MEMBER,"email","firstname","lastname",
                "streetname",1,"city","password");

        AuthorOutputDTO authorDTO = new AuthorOutputDTO(1,"firstname","lastname");
        BookOutputDTO bookDTO = new BookOutputDTO(1,"title",authorDTO,"description","isbn");

        Rental rental = new Rental(user, book, LocalDate.now().plusWeeks(3));
        RentalOutputDTO rentalDTO = new RentalOutputDTO(1,userDTO,bookDTO,LocalDate.now().plusWeeks(3));

        when(rentalMapper.rentalToOutputDTO(rental)).thenReturn(rentalDTO);
        when(rentalRepository.getAll()).thenReturn(List.of(rental));

        List<RentalOutputDTO> result = rentalService.getAllRentalsDTO();

        verify(rentalMapper).rentalToOutputDTO(rental);

        assertThat(result).isEqualTo(List.of(rentalDTO));

    }

    @Test
    public void whenGetAllRentalsFromUser_thenReturnListOfRentalOutputDTOFromInputUser() {
        UserOutputDTO userDTO = new UserOutputDTO(1,MEMBER,"email","firstname","lastname",
                "streetname",1,"city","password");

        AuthorOutputDTO authorDTO = new AuthorOutputDTO(1,"firstname","lastname");
        BookOutputDTO bookDTO = new BookOutputDTO(1,"title",authorDTO,"description","isbn");

        Rental rental = new Rental(user, book, LocalDate.now().plusWeeks(3));
        long userId = user.getId();
        RentalOutputDTO rentalDTO = new RentalOutputDTO(1,userDTO,bookDTO,LocalDate.now().plusWeeks(3));

        when(userRepository.getById(userId)).thenReturn(user);
        when(rentalMapper.rentalToOutputDTO(rental)).thenReturn(rentalDTO);
        when(rentalRepository.getAll()).thenReturn(List.of(rental));

        List<RentalOutputDTO> result = rentalService.getAllRentalsFromUser(userId);

        verify(userRepository).getById(userId);
        verify(rentalMapper).rentalToOutputDTO(rental);

        assertThat(result).isEqualTo(List.of(rentalDTO));
    }

    @Test
    public void givenRentalNotOverDueExists_whenReturnBook_thenReturnCorrectString(){
        Rental rental = new Rental(user, book, LocalDate.now().plusWeeks(3));
        long id = rental.getId();

        when(rentalRepository.getById(id)).thenReturn(rental);
        String result = rentalService.returnBook(id);

        verify(rentalRepository).getById(id);
        verify(rentalRepository).delete(rental);

        assertThat(rentalService.getAllRentalsDTO()).isEmpty();
        assertThat(book.isRented()).isFalse();
        assertThat(result).isEqualTo("Rental with title title and id "+id+" was returned ");
    }

    @Test
    public void givenRentalOverDueExists_whenReturnBook_thenReturnCorrectString(){
        Rental rental = new Rental(user, book, LocalDate.now().minusDays(3));
        long id = rental.getId();

        when(rentalRepository.getById(id)).thenReturn(rental);

        String result = rentalService.returnBook(id);

        verify(rentalRepository).getById(id);
        verify(rentalRepository).delete(rental);

        assertThat(rentalService.getAllRentalsDTO()).isEmpty();
        assertThat(book.isRented()).isFalse();
        assertThat(result).isEqualTo("Rental with title title and id 1 was returned and was late, resulting in a fine");

    }

    @Test
    public void whenGetAllRentalsOverDue_thenReturnListOfRentalOutputDTO() {


        UserOutputDTO userDTO = new UserOutputDTO(1,MEMBER,"email","firstname","lastname",
                "streetname",1,"city","password");

        AuthorOutputDTO authorDTO = new AuthorOutputDTO(1,"firstname","lastname");
        BookOutputDTO bookDTO = new BookOutputDTO(1,"title",authorDTO,"description","isbn");

        LocalDate dueDate = LocalDate.now().plusDays(1);
        Rental rental = new Rental(user, book, dueDate);
        RentalOutputDTO rentalDTO = new RentalOutputDTO(1,userDTO,bookDTO,dueDate);

        when(rentalMapper.rentalToOutputDTO(rental)).thenReturn(rentalDTO);
        when(rentalRepository.getAll()).thenReturn(List.of(rental));

        List<RentalOutputDTO> result = rentalService.getAllRentalsOverdue();

        verify(rentalRepository).getAll();
        verify(rentalMapper).rentalToOutputDTO(rental);

        assertThat(result).isEqualTo(List.of(rentalDTO));
    }


}
