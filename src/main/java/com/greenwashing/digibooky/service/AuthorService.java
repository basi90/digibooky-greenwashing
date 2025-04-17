package com.greenwashing.digibooky.service;

import com.greenwashing.digibooky.infrastructure.AuthorRepository;
import com.greenwashing.digibooky.service.mappers.AuthorMapper;
import org.springframework.stereotype.Service;

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
}