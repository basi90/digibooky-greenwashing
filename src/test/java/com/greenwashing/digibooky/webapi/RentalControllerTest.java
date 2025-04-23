package com.greenwashing.digibooky.webapi;
import com.greenwashing.digibooky.domain.Author;
import com.greenwashing.digibooky.domain.Book;
import com.greenwashing.digibooky.infrastructure.AuthorRepository;
import com.greenwashing.digibooky.infrastructure.BookRepository;
import com.greenwashing.digibooky.service.DTOs.*;
import com.greenwashing.digibooky.service.RentalService;
import com.greenwashing.digibooky.service.UserService;
import com.greenwashing.digibooky.service.mappers.RentalMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

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

    private final Author TEST_AUTHOR = new Author("firstname","lastname");
    private final LocalDate DATE_YESTERDAY = LocalDate.now().minusDays(1);
    private final LocalDate DATE_TOMORROW = LocalDate.now().plusDays(1);



    @Autowired
    private RentalService rentalService;
    @Autowired
    private BookRepository bookRepository;

    private List<RentalOutputDTO> RENTAL_DTOS;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    private String basicAuth(String email, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((email + ":" + password).getBytes());
    }

    @Test
    @Order(1)
    void whenGetAllRentalsAsLibrarian_thenReturnAllRentalsDTO() {
        RentalInputDTO rentalPayload1 = new RentalInputDTO(3, 2,DATE_TOMORROW);
        RentalInputDTO rentalPayload2 = new RentalInputDTO(3, 3,DATE_YESTERDAY);

        rentalService.borrowBook(rentalPayload1);
        rentalService.borrowBook(rentalPayload2);

        RENTAL_DTOS  = rentalService.getAllRentalsDTO();

        RentalOutputDTO[] response = given()
                .header("Authorization", LIB_AUTH)
                .when()
                .get("/rentals")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RentalOutputDTO[].class);

        assertThat(response).containsExactlyInAnyOrderElementsOf(RENTAL_DTOS);
    }

    @Test
    @Order(2)
    void whenBorrowBookAsRegisteredUser_thenReturnRentalDTO() {
        //SETUP
        Book newBook1 = new Book("newtitle1", TEST_AUTHOR,"newbook1","newisbn1");
        bookRepository.save(newBook1);
        RentalInputDTO payload = new RentalInputDTO(3, newBook1.getId(), DATE_TOMORROW);
        long idDTO = rentalService.getAllRentalsDTO().size()+1;


        RentalOutputDTO response = given()
                .header("Authorization", MEM_AUTH)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/rentals")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(RentalOutputDTO.class);

        assertThat(response.getId()).isEqualTo(idDTO);
        assertThat(response.getBook().getTitle()).isEqualTo(newBook1.getTitle());
        assertThat(response.getBook().getAuthor().getFirstName()).isEqualTo(TEST_AUTHOR.getFirstName());
        assertThat(response.getBook().getIsbn()).isEqualTo(newBook1.getIsbn());
        assertThat(response.getUser().getEmail()).isEqualTo(MEMBER_EMAIL);
    }

    @Test
    @Order(3)
    void whenReturnBookAsRegisteredUser_thenReturnCorrectString() {

        String response = given()
                .header("Authorization", MEM_AUTH)
                .when()
                .delete("/rentals/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().asString();

        assertThat(response).isEqualTo("Rental with title newtitle1" +
                " and id 1 was returned ");
    }

    @Test
    @Order(4)
    void whenGetAllRentalsOverdue_thenReturnAllRentalsOverdueDTO() {

        RentalOutputDTO[] response = given()
                .header("Authorization", LIB_AUTH)
                .when()
                .get("/rentals/overdue")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RentalOutputDTO[].class);

        assertThat(response.length).isEqualTo(1);

    }

    @Test
    @Order(5)
    void whenGetAllRentalsFromUser_thenReturnAllUserRentalsDTO() {

        RentalOutputDTO[] response = given()
                .header("Authorization", LIB_AUTH)
                .when()
                .get("/rentals/users/2")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RentalOutputDTO[].class);

        System.out.println(rentalService.getAllRentalsFromUser(3).size());

        assertThat(response.length).isEqualTo(3);

    }


}