package com.greenwashing.digibooky.service.DTOs;

import com.greenwashing.digibooky.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

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
                {}
                """;
    }
}
