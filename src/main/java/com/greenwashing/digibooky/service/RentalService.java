package com.greenwashing.digibooky.service;

import com.greenwashing.digibooky.domain.Book;
import com.greenwashing.digibooky.domain.Rental;
import com.greenwashing.digibooky.domain.User;
import com.greenwashing.digibooky.infrastructure.BookRepository;
import com.greenwashing.digibooky.infrastructure.RentalRepository;
import com.greenwashing.digibooky.infrastructure.UserRepository;
import com.greenwashing.digibooky.service.DTOs.RentalInputDTO;
import com.greenwashing.digibooky.service.DTOs.RentalOutputDTO;
import com.greenwashing.digibooky.service.mappers.RentalMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalService {
    private RentalMapper rentalMapper;
    private RentalRepository rentalRepository;
    private UserRepository userRepository;
    private BookRepository bookRepository;


    public RentalService(RentalMapper rentalMapper, RentalRepository rentalRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.rentalMapper = rentalMapper;
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public RentalOutputDTO borrowBook(RentalInputDTO rentalInputDTO) {
        Book book = this.bookRepository.getById(rentalInputDTO.getBookId()).orElse(null);
        if(book == null) {
            throw new IllegalArgumentException("Book id " + rentalInputDTO.getBookId() + " does not exist in database");
        }

        if(!book.isRented()) {
            book.setRented(true);
            Rental rental = this.rentalMapper.inputDTOToRental(rentalInputDTO);
            this.rentalRepository.save(rental);
            return this.rentalMapper.rentalToOutputDTO(rental);
        }else {
            throw new IllegalArgumentException("Book " + book.getTitle() + " is already borrowed");
        }
        //get book by id
        //flip isRented boolean after check =ing if its available
        //map input dto to rental object
        //save in repo
        //Map Rental to DOT
        //return RentalOutputDTO
    }

    public List<RentalOutputDTO> getAllRentalsDTO() {
        return rentalRepository.getAll().stream()
                .map(r->this.rentalMapper.rentalToOutputDTO(r))
                .toList();
        //get all rentals from repo
        //map to DTO
        //collect and return list
    }

    //nice to have
    private String calculateFine (Rental rental) {
        if(rental.getReturnDate().isBefore(LocalDate.now())) {
            return "and was late, resulting in a fine";
        }else{
            return "";
        }
    }

    //maybe this one should actually return a BookOutputDTO? --> when a rental is returned, you get info about the book you just returned
    public String returnBook(long rentalId) {
        Rental rental = this.rentalRepository.getById(rentalId);
        Book book = rental.getBook();

        book.setRented(false);
        this.rentalRepository.delete(rental);
        return "Rental with title "+book.getTitle()+" and id "+rental.getId()+" was returned "+calculateFine(rental);
        //get the rental object and store in local var
        //delete rental from repo
        //check dates calculate fine (method)
        //determine fine if applicable and show message
        //flip isRented on book from rental local var
    }

    public List<RentalOutputDTO> getAllRentalsFromUser(long userId) {
        User user = userRepository.getById(userId);
        return this.rentalRepository.getAll().stream()
                .filter(r -> r.getUser().equals(user))
                .map(r -> this.rentalMapper.rentalToOutputDTO(r))
                .toList();
        //get al rentals from repo
        //get the user from the repo
        //filter in stream, only keep rentals from said found user
        //map them to DTO
        //return list of rentalOutputDTOs
    }

    public List<RentalOutputDTO> getAllRentalsOverdue() {
        return rentalRepository.getAll().stream()
                .filter(r -> r.getReturnDate().isAfter(LocalDate.now()))
                .map(r -> this.rentalMapper.rentalToOutputDTO(r))
                .toList();

        //get all rentals from repo
        //stream and filter, only keep rentals whos due date are overdue
        //map to dto
        //collect and return list
    }


}
