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
        populateAuthors();
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


    // FILLER
    private void populateAuthors () {
        Author author1 = new Author("Lao", "Tse");
        Author author2 = new Author("Miyamoto", "Musashi");
        Author author3 = new Author("Ikkyu", "Sojun");

        authors.put(author1.getId(), author1);
        authors.put(author2.getId(), author2);
        authors.put(author3.getId(), author3);
    }
}
