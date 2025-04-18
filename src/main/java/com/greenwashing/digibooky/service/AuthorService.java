package com.greenwashing.digibooky.service;

import com.greenwashing.digibooky.domain.Author;
import com.greenwashing.digibooky.infrastructure.AuthorRepository;
import com.greenwashing.digibooky.service.DTOs.AuthorInputDTO;
import com.greenwashing.digibooky.service.DTOs.AuthorOutputDTO;
import com.greenwashing.digibooky.service.mappers.AuthorMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
        // get all authors from the repository
        // map it to a list of dto
        // return the list of dto
        return null;
    }

    // get by id
    public AuthorOutputDTO getById(long id) {
        // get the author matching the id from repo
            // if not present, handle error with custom exception
        // map the author to a dto
        // return the dto
        return null;
    }

    // update
    public AuthorOutputDTO save(AuthorInputDTO dto) {
        // map the input dto to an actual author
        // save it in the repository
        // map the saved author to an output dto
        // return the dto
        return null;
    }

    // delete
    public void delete(long id) {
        // delete the matching book from repo with id
        // if not present, handle error with custom exception
    }

    // add // do we actually need this method?
    public AuthorOutputDTO add(long id) {
        // map the input dto to an actual author
        // save it in the repository
        // map the saved author to an output dto
        // return the dto
        return null;
    }
}