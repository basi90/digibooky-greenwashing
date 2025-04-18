package com.greenwashing.digibooky.infrastructure;

import com.greenwashing.digibooky.domain.Author;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

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

    public boolean delete(long id) {
        return authors.remove(id) != null;
    }

    public Optional<Author> getById(long id) {
        return Optional.ofNullable(authors.get(id));
    }

    public Collection<Author> getAll() {
        return authors.values();
    }
}
