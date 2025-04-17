package com.greenwashing.digibooky.service.DTOs;

public class BookInputDTO {
    private String title;
    private long authorId;
    private String description;
    private String isbn;

    public BookInputDTO(String title, long authorId, String description, String isbn) {
        this.title = title;
        this.authorId = authorId;
        this.description = description;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public long getAuthorId() {
        return authorId;
    }

    public String getDescription() {
        return description;
    }

    public String getIsbn() {
        return isbn;
    }
}
