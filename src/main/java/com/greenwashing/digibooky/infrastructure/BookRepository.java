package com.greenwashing.digibooky.infrastructure;

import com.greenwashing.digibooky.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

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

    public boolean delete(long id) {
        if (!books.containsKey(id)) {
            return false;
        }
        deleted.put(id, books.get(id));
        books.remove(id);
        return true;
    }

    public Optional<Book> getById(long id) {
        return Optional.ofNullable(books.get(id));
    }

    public Collection<Book> getAll() {
        return books.values();
    }
}
