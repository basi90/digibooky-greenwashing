package com.greenwashing.digibooky.service.DTOs;

import java.util.Objects;

public class BookOutputDTO {
    private long id;
    private String title;
    private AuthorOutputDTO author;
    private String description;
    private String isbn;

    public BookOutputDTO(long id, String title, AuthorOutputDTO author, String description, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public AuthorOutputDTO getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookOutputDTO that = (BookOutputDTO) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(author, that.author)&& Objects.equals(description, that.description)&&Objects.equals(isbn, that.isbn);
    }
}
