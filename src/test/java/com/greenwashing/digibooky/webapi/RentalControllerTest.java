package com.greenwashing.digibooky.webapi;
import com.greenwashing.digibooky.domain.Author;
import com.greenwashing.digibooky.domain.Book;
import com.greenwashing.digibooky.domain.Rental;
import com.greenwashing.digibooky.domain.UserRole;
import com.greenwashing.digibooky.infrastructure.BookRepository;
import com.greenwashing.digibooky.infrastructure.RentalRepository;
import com.greenwashing.digibooky.infrastructure.UserRepository;
import com.greenwashing.digibooky.service.DTOs.AuthorOutputDTO;
import com.greenwashing.digibooky.service.DTOs.BookOutputDTO;
import com.greenwashing.digibooky.service.DTOs.RentalOutputDTO;
import com.greenwashing.digibooky.service.DTOs.UserInputDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RentalControllerTest {
    @LocalServerPort
    private int port;

    private final String LIBRARIAN_EMAIL = "librarian@librarian.com";
    private final String LIBRARIAN_PASS = "librarian";

    private final String MEMBER_EMAIL = "member@member.com";
    private final String MEMBER_PASS = "member";

    private final String LIB_AUTH = basicAuth(LIBRARIAN_EMAIL, LIBRARIAN_PASS);
    private final String MEM_AUTH = basicAuth(MEMBER_EMAIL, MEMBER_PASS);


    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;


    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    private String basicAuth(String email, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((email + ":" + password).getBytes());
    }

    @Test
    void whenGetAllRentalsAsLibrarian_thenReturnAllRentalsDTO() {

        given()
                .header("Authorization", LIB_AUTH)
                .when()
                .get("/rentals")
                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat().body("size()", equalTo(2))
                .assertThat().body("[0].id", equalTo(1));
    }
}