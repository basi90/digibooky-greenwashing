package com.greenwashing.digibooky.infrastructure;

import com.greenwashing.digibooky.domain.User;
import com.greenwashing.digibooky.domain.UserRole;
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

        User admin =  new User(
                UserRole.ADMIN,
                "ssnadmin",
                "admin@admin.com",
                "admin",
                "admin",
                "street",
                1,
                "Brussels",
                "1040",
                "admin"
        );
        users.put(admin.getId(), admin);
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
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}