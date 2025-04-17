package com.greenwashing.digibooky.service.DTOs;

public class BookEnhancedDTO {

    // FIELDS
    private long id;
    private String title;
    private AuthorOutputDTO author;
    private String description;
    private String isbn;
    private boolean isRented;
    private UserOutputDTO user;

    public BookEnhancedDTO(long id, String title, AuthorOutputDTO author,
                           String description, String isbn, boolean isRented,
                           UserOutputDTO user) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
    }
}