package com.greenwashing.digibooky.webapi;
import com.greenwashing.digibooky.domain.UserRole;
import com.greenwashing.digibooky.service.DTOs.UserInputDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RentalControllerTest {
    @LocalServerPort
    private int port;

    private final String LIBRARIAN_EMAIL = "librarian@librarian.com";
    private final String LIBRARIAN_PASS = "librarian";

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    private String basicAuth(String email, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((email + ":" + password).getBytes());
    }

    @Test
    void whenGetAllRentalsAsMember_thenReturnAllRentalsDTO() {

    }
}
