package com.greenwashing.digibooky.webapi;

import com.greenwashing.digibooky.domain.UserRole;
import com.greenwashing.digibooky.infrastructure.AuthorRepository;
import com.greenwashing.digibooky.infrastructure.BookRepository;
import com.greenwashing.digibooky.service.DTOs.AuthorInputDTO;
import com.greenwashing.digibooky.service.DTOs.AuthorOutputDTO;
import com.greenwashing.digibooky.service.DTOs.BookInputDTO;
import com.greenwashing.digibooky.service.DTOs.UserInputDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.Base64;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    void testCreateNewBook() {

        System.out.println(bookRepository.getAll());
        System.out.println(authorRepository.getAll());
        System.out.println(authorRepository.getById(1L));

        BookInputDTO inputDTO = new BookInputDTO(
                "The Great Gatsby",
                1L,
                "Some classic book.",
                "9780743273565"
        );

        given()
                .contentType(ContentType.JSON)
                .body(inputDTO)
                .when()
                .post("/books")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("title", equalTo("The Great Gatsby"))
                .body("author.id", equalTo(1))
                .body("isbn", equalTo("9780743273565"));

    }

}

