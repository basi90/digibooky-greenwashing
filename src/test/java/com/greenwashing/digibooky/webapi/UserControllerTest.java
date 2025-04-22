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
public class UserControllerTest {

    @LocalServerPort
    private int port;

    private final String ADMIN_EMAIL = "admin@admin.com";
    private final String ADMIN_PASS = "admin";


    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    private String basicAuth(String email, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((email + ":" + password).getBytes());
    }

    @Test
    void testRegisterMemberAndGetAllUsersAsAdmin() {
        //Default admin
        String adminAuth = basicAuth(ADMIN_EMAIL, ADMIN_PASS);

        // Register member
        UserInputDTO memberDTO = new UserInputDTO(UserRole.MEMBER, "a", "x@y.z", "a","b","c",1,"Brussels", "e", "pass");

        given()
                .contentType(ContentType.JSON)
                .body(memberDTO)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("email", equalTo("x@y.z"))
                .body("role", equalTo("MEMBER"));

        // Get all users as admin
        given()
                .header("Authorization", adminAuth)
                .when()
                .get("/users")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("size()", greaterThanOrEqualTo(1))
                .body("[0].email", notNullValue());
    }

    @Test
    void testRegisterLibrarianAsAdmin() {
        //Default admin
        String adminAuth = basicAuth(ADMIN_EMAIL, ADMIN_PASS);

        UserInputDTO librarianDTO = new UserInputDTO(UserRole.LIBRARIAN, "a", "lib@lib.com", "a", "b", "c", 1, "Brussels", "e", "pass");
        // Register Librarian with credentials
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", adminAuth)
                .body(librarianDTO)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("email", equalTo("lib@lib.com"))
                .body("role", equalTo("LIBRARIAN"));

        // Register Librarian without credentials
        given()
                .contentType(ContentType.JSON)
                .body(librarianDTO)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void testRegisterAdminAsAdmin() {
        //Default admin
        String adminAuth = basicAuth(ADMIN_EMAIL, ADMIN_PASS);

        UserInputDTO adminDTO = new UserInputDTO(UserRole.ADMIN, "a", "admin@admin.com", "a", "b", "c", 1, "Brussels", "e", "admin");

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", adminAuth)
                .body(adminDTO)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("email", equalTo("admin@admin.com"))
                .body("role", equalTo("ADMIN"));

        given()
                .contentType(ContentType.JSON)
                .body(adminDTO)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}