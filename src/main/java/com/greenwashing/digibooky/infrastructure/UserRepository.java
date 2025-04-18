package com.greenwashing.digibooky.infrastructure;

import com.greenwashing.digibooky.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;

@Repository
public class UserRepository {

    // FIELDS
    private HashMap<Long, User> users;

    // CONSTRUCTOR
    public UserRepository() {
        users = new HashMap<>();
    }

    // METHODS
    public void save(User user) {
        users.put(user.getId(), user);
    }

    public void delete(User user) {
        users.remove(user.getId());
    }

    public User getById(long id) {
        return users.get(id);
    }

    public Collection<User> getAll() {
        return users.values();
    }

    public User getByEmail(String email) {
        return users.get(email);
    }
}