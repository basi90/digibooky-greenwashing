package com.greenwashing.digibooky.service;

import com.greenwashing.digibooky.domain.Rental;
import com.greenwashing.digibooky.infrastructure.BookRepository;
import com.greenwashing.digibooky.infrastructure.UserRepository;
import com.greenwashing.digibooky.service.DTOs.BookInputDTO;
import com.greenwashing.digibooky.service.DTOs.RentalInputDTO;
import com.greenwashing.digibooky.service.DTOs.RentalOutputDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {
    private UserRepository userRepository;
    private BookRepository bookRepository;

    public RentalService(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public RentalOutputDTO borrowBook(long userId, String isbn) {
        //get user by isbn
        //flip isRented boolean!!!
        //create a rental
        //save in repo
        //Map Rental to DOT
        //return RentalOutputDTO
        return null;
    }

    public List<RentalOutputDTO> getAllRentalsDTO() {
        //get all rentals from repo
        //map to DTO
        //collect and return list
        return null;
    }

    public void returnBook(long rentalId) {
        //get the rental object and store in local var
        //delete rental from repo
        //check dates
        //determine fine if applicable and show message
        //flip isRented on book from rental local var
    }

    public List<RentalOutputDTO> getAllRentalsFromUs(long userId) {
        //get al rentals from repo
        //get the user from the repo
        //filter in stream, only keep rentals from said found user
        //map them to DTO
        //return list of rentalOutputDTOs
        return null;
    }

    public List<RentalOutputDTO> getAllRentalsOverdue() {
        //get all rentals from repo
        //stream and filter, only keep rentals whos due date are overdue
        //map to dto
        //collect and return list
        return null;
    }


}
