package com.greenwashing.digibooky.service.mappers;

import com.greenwashing.digibooky.domain.User;
import com.greenwashing.digibooky.service.DTOs.UserInputDTO;
import com.greenwashing.digibooky.service.DTOs.UserOutputDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private UserMapper() {}

    public User inputDTOtoUser(UserInputDTO userInputDTO) {
        return new User(userInputDTO.getRole(),
                userInputDTO.getSsn(),
                userInputDTO.getEmail(),
                userInputDTO.getFirstName(),
                userInputDTO.getLastName(),
                userInputDTO.getStreetName(),
                userInputDTO.getStreetNumber(),
                userInputDTO.getCity(),
                userInputDTO.getPostalCode(),
                userInputDTO.getPassword());

    }

    public UserOutputDTO userToOutputDTO(User user) {
        return new UserOutputDTO(user.getId(),
                user.getRole(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getStreetName(),
                user.getStreetNumber(),
                user.getCity(),
                user.getPostalCode());
    }
}
