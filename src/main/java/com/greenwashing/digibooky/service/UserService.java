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
        //replace if statement with validation later (if time permits)
        User newMember = userMapper.inputDTOtoUser(memberAccountDetails);
        userRepository.save(newMember);
        return userMapper.userToOutputDTO(newMember);
    }

    public UserOutputDTO registerLibrarian(UserInputDTO librarianAccountDetails) {
        if(!librarianAccountDetails.getRole().equals(UserRole.LIBRARIAN)) {
            throw new IllegalArgumentException("The payload role should be LIBRARIAN");
        }
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
        //replace if statement with validation later (if time permits)
        User newMember = userMapper.inputDTOtoUser(adminAccountDetails);
        userRepository.save(newMember);
        return userMapper.userToOutputDTO(newMember);
    }








}
