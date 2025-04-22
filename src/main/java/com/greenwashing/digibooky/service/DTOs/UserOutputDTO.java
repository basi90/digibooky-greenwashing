package com.greenwashing.digibooky.service.DTOs;

import com.greenwashing.digibooky.domain.UserRole;

public class UserOutputDTO {
    private long id;
    private UserRole role;
    private String email;
    private String firstName;
    private String lastName;
    private String streetName;
    private int streetNumber;
    private String city;
    private String postalCode;

    public UserOutputDTO(long id, UserRole userRole, String email, String firstName, String lastName, String streetName, int streetNumber, String city, String postalCode) {
        this.id = id;
        this.role = userRole;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserOutputDTO that = (UserOutputDTO) o;
        return id == that.id && role == that.role && email.equals(that.email) && firstName.equals(that.firstName)
                && lastName.equals(that.lastName) && streetName.equals(that.streetName) && streetNumber == that.streetNumber
                && city.equals(that.city) && postalCode.equals(that.postalCode);
    }
}
