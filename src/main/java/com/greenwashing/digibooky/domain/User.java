package com.greenwashing.digibooky.domain;

import java.util.Objects;

public class User {
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
    private String password;

    private static long nextId = 1;

    public User(UserRole role, String ssn, String email, String lastName, String city, String password) {
        this.role = role;
        this.ssn = ssn;
        this.email = email;
        this.lastName = lastName;
        this.city = city;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User " + id + " " + role + " " + email + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
