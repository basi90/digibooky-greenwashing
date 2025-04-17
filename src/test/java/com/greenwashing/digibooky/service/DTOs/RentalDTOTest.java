package com.greenwashing.digibooky.service.DTOs;


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
        RentalInputDTO inputDTO = new RentalInputDTO(1,1, 2025-01-10-10-54);

        String jsonString = """
                {
                "userId":1,
                "bookId":1,
                "returnDate":
                }
                """;

        assertThat(inputJson.write(inputDTO)).isEqualTo(jsonString);
    }
}
