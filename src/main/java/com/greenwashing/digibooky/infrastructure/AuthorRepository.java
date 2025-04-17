package com.greenwashing.digibooky.infrastructure;

import com.greenwashing.digibooky.domain.Author;
import com.greenwashing.digibooky.service.DTOs.AuthorOutputDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AuthorRepository {

    // FIELDS
    HashMap<Long, Author> authors;

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
