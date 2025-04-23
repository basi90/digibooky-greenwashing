package com.greenwashing.digibooky.service;

import com.greenwashing.digibooky.domain.User;
import com.greenwashing.digibooky.domain.UserRole;
import com.greenwashing.digibooky.infrastructure.UserRepository;
import com.greenwashing.digibooky.service.DTOs.UserInputDTO;
import com.greenwashing.digibooky.service.DTOs.UserOutputDTO;
import com.greenwashing.digibooky.service.mappers.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserMapper userMapper;
    private UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public List<UserOutputDTO> viewMembersAsAdmin() {
        return userRepository.getAll()
                .stream()
                .map(user->userMapper.userToOutputDTO(user))
                .toList();
    }

    public UserOutputDTO registerAsMember(UserInputDTO memberAccountDetails) {
        if(!memberAccountDetails.getRole().equals(UserRole.MEMBER)) {
            throw new IllegalArgumentException("The payload role should be MEMBER");
        }

        validateUserInputDTO(memberAccountDetails);

        //replace if statement with validation later (if time permits)
        User newMember = userMapper.inputDTOtoUser(memberAccountDetails);
        userRepository.save(newMember);
        return userMapper.userToOutputDTO(newMember);
    }

    public UserOutputDTO registerLibrarian(UserInputDTO librarianAccountDetails) {
        if(!librarianAccountDetails.getRole().equals(UserRole.LIBRARIAN)) {
            throw new IllegalArgumentException("The payload role should be LIBRARIAN");
        }

        validateUserInputDTO(librarianAccountDetails);

        //replace if statement with validation later (if time permits)
        User newMember = userMapper.inputDTOtoUser(librarianAccountDetails);
        userRepository.save(newMember);
        return userMapper.userToOutputDTO(newMember);
    }

    //this one is nice to have not must-have
    public UserOutputDTO registerAdmin(UserInputDTO adminAccountDetails) {
        if(!adminAccountDetails.getRole().equals(UserRole.ADMIN)) {
            throw new IllegalArgumentException("The payload role should be ADMIN");
        }

        validateUserInputDTO(adminAccountDetails);

        //replace if statement with validation later (if time permits)
        User newMember = userMapper.inputDTOtoUser(adminAccountDetails);
        userRepository.save(newMember);
        return userMapper.userToOutputDTO(newMember);
    }

    private void validateUserInputDTO(UserInputDTO dto) {
        validateSsn(dto);
        validateEmail(dto);
        validateLastName(dto);
        validateCity(dto);
    }

    private void validateSsn(UserInputDTO dto) {
        if (dto.getSsn() == null || dto.getSsn().trim().isEmpty()) {
            throw new IllegalArgumentException("SSN is required");
        }
        boolean ssnExists = userRepository.getAll().stream()
                .anyMatch(user -> user.getSsn()
                .equals(dto.getSsn()));
        if (ssnExists) {
            throw new IllegalArgumentException("SSN is already in use");
        }
    }

    private void validateEmail(UserInputDTO dto) {
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (!dto.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Email format is invalid");
        }

        boolean emailExists = userRepository.getAll().stream()
                .anyMatch(user -> user.getEmail()
                .equals(dto.getEmail()));
        if (emailExists) {
            throw new IllegalArgumentException("Email is already in use");
        }
    }

    private void validateLastName(UserInputDTO dto) {
        if (dto.getLastName() == null || dto.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name is required");
        }
    }

    private void validateCity(UserInputDTO dto) {
        if (dto.getCity() == null || dto.getCity().trim().isEmpty()) {
            throw new IllegalArgumentException("City is required");
        }
    }
}
