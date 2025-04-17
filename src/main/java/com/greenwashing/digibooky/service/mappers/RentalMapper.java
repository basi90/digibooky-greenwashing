package com.greenwashing.digibooky.service.mappers;

import com.greenwashing.digibooky.domain.Rental;
import com.greenwashing.digibooky.infrastructure.BookRepository;
import com.greenwashing.digibooky.infrastructure.UserRepository;
import com.greenwashing.digibooky.service.DTOs.RentalInputDTO;
import com.greenwashing.digibooky.service.DTOs.RentalOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {
    UserRepository userRepository;
    BookRepository bookRepository;
    UserMapper userMapper;
    BookMapper bookMapper;

    @Autowired
    public  RentalMapper (UserRepository userRepository, BookRepository bookRepository, UserMapper userMapper, BookMapper bookMapper) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public Rental inputDTOToRental(RentalInputDTO rentalInputDTO) {
        return new Rental(this.userRepository.getById(rentalInputDTO.getUserId()),
                this.bookRepository.getById(rentalInputDTO.getBookId()),
                rentalInputDTO.getReturnDate());
    }

    public RentalOutputDTO rentalToOutputDTO(Rental rental) {
        return new RentalOutputDTO(rental.getId(),
                this.userMapper.userToOutputDTO(rental.getUser()),
                this.bookMapper.bookToOutputDTO(rental.getBook()),
                rental.getReturnDate());
    }
}
