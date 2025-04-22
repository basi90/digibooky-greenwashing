package com.greenwashing.digibooky.service.DTOs;


import com.greenwashing.digibooky.domain.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDate;

@JsonTest
public class RentalDTOTest {

    @Autowired
    private JacksonTester<RentalInputDTO> inputJson;

    @Autowired
    private JacksonTester<RentalOutputDTO> outputJson;

    @Test
    void testSerializeInputDTO() throws IOException {

        LocalDate returnDate = LocalDate.of(2025, 1, 10);
        RentalInputDTO inputDTO = new RentalInputDTO(1, 1, returnDate);

        String jsonString = """
                {
                "userId":1,
                "bookId":1,
                "returnDate":"2025-01-10"
                }
                """;

        assertThat(inputJson.write(inputDTO)).isEqualToJson(jsonString);
    }

    @Test
    void testSerializeOutputDTO() throws IOException {
        LocalDate returnDate = LocalDate.of(2025, 1, 10);
        AuthorOutputDTO authorDTO = new AuthorOutputDTO(1,"a", "b");
        BookOutputDTO bookDTO = new BookOutputDTO(1, "a", authorDTO, "b", "c");
        UserOutputDTO userDTO = new UserOutputDTO(1, UserRole.MEMBER,  "x@y.z", "c", "d", "e", 1, "Brussels", "f");

        RentalOutputDTO outputDTO = new RentalOutputDTO(1, userDTO, bookDTO, returnDate);

        String jsonString = """
                {
                "id":1,
                "user":{
                    "id":1,
                    "role":"MEMBER",
                    "email":"x@y.z",
                    "firstName":"c",
                    "lastName":"d",
                    "streetName":"e",
                    "streetNumber":1,
                    "city":"Brussels",
                    "postalCode":"f"
                    },
                "book":{
                    "id":1,
                    "title":"a",
                    "author":{
                        "id":1,
                        "firstName":"a",
                        "lastName":"b"
                        },
                    "description":"b",
                    "isbn":"c"
                    },
                "returnDate":"2025-01-10"
                }
                """;

        assertThat(outputJson.write(outputDTO)).isEqualToJson(jsonString);
    }
}
