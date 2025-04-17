package com.greenwashing.digibooky.infrastructure;

import com.greenwashing.digibooky.domain.Author;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;

@Repository
public class AuthorRepository {

    // FIELDS
    private HashMap<Long, Author> authors;

    // CONSTRUCTOR
    public AuthorRepository() {
        authors = new HashMap<>();
    }

    // METHODS
    public void save(Author author) {
        authors.put(author.getId(), author);
    }

    public void delete(long id) {
        authors.remove(id);
    }

    public Author getById(long id) {
        return authors.get(id);
    }

    public Collection<Author> getAll() {
        return authors.values();
    }
}
