package com.greenwashing.digibooky.service.mappers;

import com.greenwashing.digibooky.domain.Author;
import com.greenwashing.digibooky.service.DTOs.AuthorInputDTO;
import com.greenwashing.digibooky.service.DTOs.AuthorOutputDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    // METHODS
    public AuthorOutputDTO authorToOutputDTO(Author author) {
        return new AuthorOutputDTO(
                author.getId(),
                author.getFirstName(),
                author.getLastName()
        );
    }

    public Author inputDTOToAuthor(AuthorInputDTO DTO) {
        return new Author(
                DTO.getFirstName(),
                DTO.getLastName()
        );
    }
}
