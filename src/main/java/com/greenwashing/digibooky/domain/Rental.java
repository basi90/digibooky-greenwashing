package com.greenwashing.digibooky.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Rental {
    private long id;
    private User user;
    private Book book;
    private LocalDate returnDate;

    private static long nextId = 1;

    public Rental(User user, Book book, LocalDate returnDate) {
        this.user = user;
        this.book = book;
        this.returnDate = returnDate;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return "Rental " + id + " " + user.toString() + " " + book.toString() + " " + returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return getId() == rental.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
