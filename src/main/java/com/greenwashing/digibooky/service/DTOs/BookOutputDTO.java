package com.greenwashing.digibooky.service.DTOs;

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
}
