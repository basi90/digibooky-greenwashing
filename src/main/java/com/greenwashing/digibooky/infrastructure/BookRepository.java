package com.greenwashing.digibooky.infrastructure;

import com.greenwashing.digibooky.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;

@Repository
public class BookRepository {

    // FIELDS
    private HashMap<Long, Book> books;
    private HashMap<Long, Book> deleted;

    // CONSTRUCTOR
    public BookRepository() {
        books = new HashMap<>();
        deleted = new HashMap<>();
    }

    // METHODS
    public void save(Book book) {
        books.put(book.getId(), book);
    }

    public void delete(long id) {
        deleted.put(id, books.get(id));
        books.remove(id);
    }

    public Book getById(long id) {
        return books.get(id);
    }

    public Collection<Book> getAll() {
        return books.values();
    }
}
