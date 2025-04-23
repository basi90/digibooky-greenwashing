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


// Those integration tests relies on the hard-coded data in the repositories.
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

        BookInputDTO inputDTO = new BookInputDTO(
                "The Great Gatsby",
                2,
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
                .body("author.id", equalTo(2))
                .body("isbn", equalTo("9780743273565"));

    }

    @Test
    void testGetAllBooks() {
        when()
                .get("/books")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", greaterThanOrEqualTo(3))
                .body("[0].title", notNullValue())
                .body("[0].author.id", notNullValue())
                .body("[0].isbn", notNullValue());
    }

    @Test
    void testGetBookById() {
        when()
                .get("/books/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("title", equalTo("The Tao Te Ching"))
                .body("author.id", equalTo(1))
                .body("isbn", equalTo("9781234567897"))
                .body("author.firstName", equalTo("Lao"))
                .body("author.lastName", equalTo("Tse"));
    }

    @Test
    void testGetBookByIdEnhancedWhenNotRented() {
        when()
                .get("/books/1/enhanced")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("title", equalTo("The Tao Te Ching"))
                .body("author.id", equalTo(1))
                .body("isbn", equalTo("9781234567897"))
                .body("author.firstName", equalTo("Lao"))
                .body("author.lastName", equalTo("Tse"))
                .body("isRented", equalTo("No"));
    }
    /* Not sure how to make this work.
    @Test
    void testGetBookByIdEnhancedWhenRented() {
        // renting book 2
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin@admin.com:admin".getBytes()))
                .body("{\"userId\": 1}")
                .when()
                .post("/books/2/rent")
                .then()
                .statusCode(HttpStatus.OK.value());
        // testing its enhanced details
        when()
                .get("/books/2/enhanced")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("title", equalTo("The Book of Five Rings"))
                .body("author.id", equalTo(2))
                .body("isbn", equalTo("9781861978769"))
                .body("author.firstName", equalTo("Miyamoto"))
                .body("author.lastName", equalTo("Musashi"))
                .body("isRented", equalTo("Yes"));
    }
    */
    @Test
    void testSearchBookFullTitle() {
        when()
                .get("/books/search?title=The Tao Te Ching")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(1))
                .body("[0].title", equalTo("The Tao Te Ching"))
                .body("[0].author.id", equalTo(1))
                .body("[0].isbn", equalTo("9781234567897"));
    }

    @Test
    void testSearchBookPartialTitle() {
        when()
                .get("/books/search?title=*Tao*")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", greaterThanOrEqualTo(1))
                .body("[0].title", equalTo("The Tao Te Ching"))
                .body("[0].author.id", equalTo(1))
                .body("[0].isbn", equalTo("9781234567897"));
    }

    @Test
    void testSearchBookFullAuthor() {
        when()
                .get("/books/search?firstName=Lao&lastName=Tse")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", equalTo(2))
                .body("[0].title", equalTo("The Tao Te Ching"))
                .body("[0].author.id", equalTo(1))
                .body("[0].isbn", equalTo("9781234567897"));
    }

    @Test
    void testSearchPartialISBN() {
        when()
                .get("/books/search?isbn=*7*23*")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", greaterThanOrEqualTo(1))
                .body("[0].title", equalTo("The Tao Te Ching"))
                .body("[0].author.id", equalTo(1))
                .body("[0].isbn", equalTo("9781234567897"));
    }

    @Test
    void testUpdateBook() {
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
                .put("/books/3")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("title", equalTo("The Great Gatsby"))
                .body("author.id", equalTo(1))
                .body("isbn", equalTo("9781402894626")); // isbn shouldn't be updated.
    }

    @Test
    void testDeleteBook() {
        // Delete a book
        when()
                .delete("/books/2")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}

