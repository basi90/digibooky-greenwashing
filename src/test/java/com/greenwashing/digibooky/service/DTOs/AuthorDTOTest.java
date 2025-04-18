package com.greenwashing.digibooky.service.DTOs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

@JsonTest
public class AuthorDTOTest {

    @Autowired
    private JacksonTester<AuthorInputDTO> inputJson;

    @Autowired
    private JacksonTester<AuthorOutputDTO> outputJson;

    @Test
    void testSerializeInputDTO() throws IOException {
        AuthorInputDTO inputDTO = new AuthorInputDTO("a", "b");

        String jsonString = """
                {
                "firstName":"a",
                "lastName":"b"
                }
                """;

        assertThat(inputJson.write(inputDTO)).isEqualToJson(jsonString);
    }

    @Test
    void testSerializeOutputDTO() throws IOException {
        AuthorOutputDTO outputDTO = new AuthorOutputDTO(1,"a", "b");

        String jsonString = """
                {
                "id":1,
                "firstName":"a",
                "lastName":"b"
                }
                """;

        assertThat(outputJson.write(outputDTO)).isEqualToJson(jsonString);
    }
}
