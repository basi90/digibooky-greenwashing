package com.greenwashing.digibooky.service.DTOs;

import java.time.LocalDate;

public class RentalOutputDTO {
    private long id;
    private UserOutputDTO user;
    private BookOutputDTO book;
    private LocalDate returnDate;

    public RentalOutputDTO(long id, UserOutputDTO user, BookOutputDTO book, LocalDate returnDate) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.returnDate = returnDate;
    }

    public long getId() {
        return id;
    }

    public UserOutputDTO getUser() {
        return user;
    }

    public BookOutputDTO getBook() {
        return book;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}

