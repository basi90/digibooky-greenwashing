package com.greenwashing.digibooky.service;

import com.greenwashing.digibooky.domain.Author;
import com.greenwashing.digibooky.infrastructure.AuthorRepository;
import com.greenwashing.digibooky.service.DTOs.AuthorInputDTO;
import com.greenwashing.digibooky.service.DTOs.AuthorOutputDTO;
import com.greenwashing.digibooky.service.mappers.AuthorMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    // FIELDS
    private AuthorRepository repository;
    private AuthorMapper mapper;

    // CONSTRUCTOR
    public AuthorService(AuthorRepository repository, AuthorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    // METHODS
    // get all
    public List<AuthorOutputDTO> getAll() {
        return repository.getAll().stream()
                .map(mapper::authorToOutputDTO)
                .collect(Collectors.toList());
    }

    // get by id
    public AuthorOutputDTO getById(long id) {
        Author author = repository.getById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return mapper.authorToOutputDTO(author);
    }

    // update
    public AuthorOutputDTO save(AuthorInputDTO dto) {
        Author author = mapper.inputDTOToAuthor(dto);
        repository.save(author);
        return mapper.authorToOutputDTO(author);
    }

    // delete
    public void delete(long id) {
        if (!repository.delete(id)) {
            throw new RuntimeException("Author not found");
        }
    }
}