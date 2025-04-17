package com.greenwashing.digibooky.service.DTOs;

public class AuthorOutputDTO {
    private long id;
    private String firstName;
    private String lastName;
    private boolean isRented;

    public AuthorOutputDTO(long id, String firstName, String lastName, boolean isRented) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isRented = isRented;
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

    public boolean isRented() {
        return isRented;
    }
}
