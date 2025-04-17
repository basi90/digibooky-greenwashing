package com.greenwashing.digibooky.service.DTOs;

import com.greenwashing.digibooky.domain.UserRole;

public class UserOutputDTO {
    private long id;
    private UserRole role;
    private String ssn;
    private String email;
    private String firstName;
    private String lastName;
    private String streetName;
    private int streetNumber;
    private String city;
    private String postalCode;

    public UserOutputDTO(long id, UserRole userRole, String ssn, String email, String firstName, String lastName, String streetName, int streetNumber, String city, String postalCode) {
        this.id = id;
        this.role = userRole;
        this.ssn = ssn;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
        this.postalCode = postalCode;
    }

    public long getId() {
        return id;
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
}
