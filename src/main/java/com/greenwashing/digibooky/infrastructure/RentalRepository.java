package com.greenwashing.digibooky.infrastructure;

import com.greenwashing.digibooky.domain.Rental;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;

@Repository
public class RentalRepository {

    // FIELDS
    private HashMap<Long, Rental> rentals;

    // CONSTRUCTOR
    public RentalRepository() {
        rentals = new HashMap<>();
    }

    // METHODS
    public void save(Rental rental) {
        rentals.put(rental.getId(), rental);
    }

    public void delete(Rental rental) {
        rentals.remove(rental.getId());
    }

    public Rental getById(long id) {
        return rentals.get(id);
    }

    public Collection<Rental> getAll() {
        return rentals.values();
    }
}
