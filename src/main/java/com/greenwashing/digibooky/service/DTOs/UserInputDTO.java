package com.greenwashing.digibooky.service.DTOs;

import com.greenwashing.digibooky.domain.UserRole;

public class UserInputDTO {
    private UserRole role;
    private String ssn;
    private String email;
    private String firstName;
    private String lastName;
    private String streetName;
    private int streetNumber;
    private String city;
    private String postalCode;
    private String password;

    public UserInputDTO(UserRole role, String ssn, String email, String firstName, String lastName, String streetName, int streetNumber, String city, String postalCode, String password) {
        this.role = role;
        this.ssn = ssn;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public String getSsn() {
        return ssn;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreetName() {
        return streetName;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPassword() {
        return password;
    }
}
