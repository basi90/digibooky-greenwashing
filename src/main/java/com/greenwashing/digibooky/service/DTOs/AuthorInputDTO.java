package com.greenwashing.digibooky.service.DTOs;

public class AuthorInputDTO {
    private String firstName;
    private String lastName;

    public AuthorInputDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
