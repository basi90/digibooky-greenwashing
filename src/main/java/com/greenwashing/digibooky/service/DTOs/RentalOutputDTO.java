package com.greenwashing.digibooky.service.DTOs;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalOutputDTO that = (RentalOutputDTO) o;
        return id == that.id && Objects.equals(user, that.user) && Objects.equals(book, that.book) && Objects.equals(returnDate, that.returnDate);
    }
}

