package com.greenwashing.digibooky.service.DTOs;

import java.util.Objects;

public class AuthorOutputDTO {
    private long id;
    private String firstName;
    private String lastName;

    public AuthorOutputDTO(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorOutputDTO that = (AuthorOutputDTO) o;
        return id == that.id && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }
}
