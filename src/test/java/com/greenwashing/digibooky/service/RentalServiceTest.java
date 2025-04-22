package com.greenwashing.digibooky.service;


import com.greenwashing.digibooky.domain.*;
import com.greenwashing.digibooky.infrastructure.BookRepository;
import com.greenwashing.digibooky.infrastructure.RentalRepository;
import com.greenwashing.digibooky.infrastructure.UserRepository;
import com.greenwashing.digibooky.service.DTOs.*;
import com.greenwashing.digibooky.service.mappers.RentalMapper;
import com.greenwashing.digibooky.service.mappers.UserMapper;
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
    private static final UserRole LIBRARIAN = UserRole.LIBRARIAN;
    private static final UserRole ADMIN = UserRole.ADMIN;

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
        User user = new User(MEMBER,"ssn","email","lastname","city","password");
        Author author = new Author("firstName","lastName");
        Book book = new Book("title",author,"description","isbn");

        UserOutputDTO userDTO = new UserOutputDTO(1,MEMBER,"ssn","email","firstname","lastname",
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
        User user = new User(MEMBER,"ssn","email","lastname","city","password");
        Author author = new Author("firstName","lastName");
        Book book = new Book("title",author,"description","isbn");

        UserOutputDTO userDTO = new UserOutputDTO(1,MEMBER,"ssn","email","firstname","lastname",
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
        User user = new User(MEMBER,"ssn","email","lastname","city","password");
        Author author = new Author("firstName","lastName");
        Book book = new Book("title",author,"description","isbn");

        UserOutputDTO userDTO = new UserOutputDTO(1,MEMBER,"ssn","email","firstname","lastname",
                "streetname",1,"city","password");

        AuthorOutputDTO authorDTO = new AuthorOutputDTO(1,"firstname","lastname");
        BookOutputDTO bookDTO = new BookOutputDTO(1,"title",authorDTO,"description","isbn");

        Rental rental = new Rental(user, book, LocalDate.now().plusWeeks(3));
        RentalOutputDTO rentalDTO = new RentalOutputDTO(1,userDTO,bookDTO,LocalDate.now().plusWeeks(3));

        when(userRepository.getById(user.getId())).thenReturn(user);
        when(rentalMapper.rentalToOutputDTO(rental)).thenReturn(rentalDTO);
        when(rentalRepository.getAll()).thenReturn(List.of(rental));

        List<RentalOutputDTO> result = rentalService.getAllRentalsFromUser(1);

        verify(userRepository).getById(1);
        verify(rentalMapper).rentalToOutputDTO(rental);

        assertThat(result).isEqualTo(List.of(rentalDTO));
    }

    @Test
    public void whenGetAllRentalsOverDue_thenReturnListOfRentalOutputDTO() {
        User user = new User(MEMBER,"ssn","email","lastname","city","password");
        Author author = new Author("firstName","lastName");
        Book book = new Book("title",author,"description","isbn");

        UserOutputDTO userDTO = new UserOutputDTO(1,MEMBER,"ssn","email","firstname","lastname",
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
