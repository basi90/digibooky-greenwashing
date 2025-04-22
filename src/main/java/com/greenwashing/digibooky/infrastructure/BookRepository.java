package com.greenwashing.digibooky.infrastructure;

import com.greenwashing.digibooky.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookRepository {

    // FIELDS
    private HashMap<Long, Book> books;
    private HashMap<Long, Book> deleted;
    private AuthorRepository authorRepository;

    // CONSTRUCTOR
    public BookRepository(AuthorRepository authorRepository) {
        books = new HashMap<>();
        deleted = new HashMap<>();
        this.authorRepository = authorRepository;
        populateBooks();
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

    public List<Book> getAllDeleted() {
        return new ArrayList<>(deleted.values());
    }

    public Collection<Book> getAll() {
        return books.values();
    }

    // FILLER

    public void populateBooks() {
        Book book1 = new Book(
                "The Tao Te Ching",
                authorRepository.getById(1).orElse(null),
                "A classic text on Taoist philosophy.",
                "9781234567897"
        );
        Book book2 = new Book(
                "The Book of Five Rings",
                authorRepository.getById(2).orElse(null),
                "A treatise on strategy and tactics.",
                "9781861978769"
        );
        Book book3 = new Book(
                "The Zen Teachings of Ikkyu",
                authorRepository.getById(3).orElse(null),
                "A collection of teachings from the Zen master Ikkyu.",
                "9781402894626"
        );

        books.put(book1.getId(), book1);
        books.put(book2.getId(), book2);
        books.put(book3.getId(), book3);
    }
}