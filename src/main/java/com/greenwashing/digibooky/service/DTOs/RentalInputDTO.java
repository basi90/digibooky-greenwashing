package com.greenwashing.digibooky.service.DTOs;

import java.time.LocalDate;

public class RentalInputDTO {
    private long userId;
    private long bookId;
    private LocalDate returnDate;

    public RentalInputDTO(long userId, long bookId, LocalDate returnDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.returnDate = returnDate;
    }

    public long getUserId() {
        return userId;
    }

    public long getBookId() {
        return bookId;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}
