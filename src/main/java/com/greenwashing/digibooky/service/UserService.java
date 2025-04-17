package com.greenwashing.digibooky.service;

import com.greenwashing.digibooky.service.DTOs.UserInputDTO;
import com.greenwashing.digibooky.service.DTOs.UserOutputDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public UserService() {}

    public List<UserOutputDTO> viewMembersAsAdmin() {
        //IN CONTROLLER -> MAKE AUTHORISATION REQUEST
        //get all users from repo
        //map to userOutputDTO
        //collest and return list
        return null;
    }

    public UserOutputDTO registerAsMember(UserInputDTO memberAccountDetails) {
        //map userInputDTO to user !!!!ROLE MUST BE MEMBER!!!! if not --> exception! --> getter in inputdto that returns member ?
        //save that user to the repo
        //map the user to dto
        //return the mapped userOutputDTO
        return null;
    }

    public UserOutputDTO registerLibrarian(UserInputDTO librarianAccountDetails) {
        //IN CONTROLLER -> MAKE AUTHORISATION REQUEST
        //map userInputDTO to user !!!!ROLE MUST BE LIBRARIAN!!!! if not --> exception! --> getter in inputdto that returns librarian ?
        //save that user to the repo
        //map the user to dto
        //return the mapped userOutputDTO
        return null;
    }

    //this one is nice to have not must-have
    public UserOutputDTO registerAdmin(UserInputDTO adminAccountDetails) {
        //IN CONTROLLER -> MAKE AUTHORISATION REQUEST
        //map userInputDTO to user !!!!ROLE MUST BE ADMIN!!!! if not --> exception! --> getter in inputdto that returns admin ?
        //save that user to the repo
        //map the user to dto
        //return the mapped userOutputDTO
        return null;
    }






}
