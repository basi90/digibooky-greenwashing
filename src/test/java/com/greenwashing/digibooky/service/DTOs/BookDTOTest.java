package com.greenwashing.digibooky.service.DTOs;

import com.greenwashing.digibooky.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

@JsonTest
public class BookDTOTest {

    @Autowired
    private JacksonTester<BookInputDTO> inputJson;

    @Autowired
    private JacksonTester<BookOutputDTO> outputJson;

    @Test
    void testSerializeInputDTO() throws IOException {
        BookInputDTO inputDTO = new BookInputDTO("a", 1, "b", "c");

        String jsonString = """
                {
                "title":"a",
                "authorId":1,
                "description":"b",
                "isbn":"c"
                }
                """;

        assertThat(inputJson.write(inputDTO)).isEqualTo(jsonString);
    }

    @Test
    void testSerializeOutputDTO() throws IOException {
        AuthorOutputDTO authorDTO = new AuthorOutputDTO(1, "a", "b");
        BookOutputDTO outputDTO = new BookOutputDTO(1, "a", authorDTO, "b", "c");

        String jsonString = """
                {
                "id":1,
                "title":"a",
                "author":{
                    "id":1,
                    "firstName":"a",
                    "lastName":"b"
                    },
                "description":"b",
                "isbn":"c"
                }
                """;

        assertThat(outputJson.write(outputDTO)).isEqualTo(jsonString);
    }
}
