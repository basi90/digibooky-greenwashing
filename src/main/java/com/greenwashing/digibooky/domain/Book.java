package com.greenwashing.digibooky.domain;

import java.util.Objects;

public class Book {
    private long id;
    private String title;
    private Author author;
    private String description;
    private String isbn;

    private static long nextId = 1;

    public Book(String title, Author author, String description, String isbn) {
        this.id = nextId++;
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

    public Author getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        return "Book " + id + " " + title + " " + author.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getId() == book.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
