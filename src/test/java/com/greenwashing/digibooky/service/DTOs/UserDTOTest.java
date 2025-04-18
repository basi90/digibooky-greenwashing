package com.greenwashing.digibooky.service.DTOs;

import com.greenwashing.digibooky.domain.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

@JsonTest
public class UserDTOTest {

    @Autowired
    private JacksonTester<UserInputDTO> inputJson;

    @Autowired
    private JacksonTester<UserOutputDTO> outputJson;

    @Test
    void testSerializeInputDTO() throws IOException {
        UserInputDTO inputDTO = new UserInputDTO(UserRole.MEMBER, "a", "x@y.z", "c", "d", "e", 1, "Brussels", "f", "g");

        String jsonString = """
                {
                "role":"MEMBER",
                "ssn":"a",
                "email":"x@y.z",
                "firstName":"c",
                "lastName":"d",
                "streetName":"e",
                "streetNumber":1,
                "city":"Brussels",
                "postalCode":"f"
                "password":"g"
                }
                """;

        assertThat(inputJson.write(inputDTO)).isEqualToJson(jsonString);
    }

    @Test
    void testSerializeOutputDTO() throws IOException {
        UserOutputDTO outputDTO = new UserOutputDTO(1,UserRole.MEMBER, "a", "x@y.z", "c", "d", "e", 1, "Brussels", "f");

        String jsonString = """
                {
                "id":1,
                "role":"MEMBER",
                "ssn":"a",
                "email":"x@y.z",
                "firstName":"c",
                "lastName":"d",
                "streetName":"e",
                "streetNumber":1,
                "city":"Brussels",
                "postalCode":"f"
                }
                """;

        assertThat(outputJson.write(outputDTO)).isEqualToJson(jsonString);
    }
}
