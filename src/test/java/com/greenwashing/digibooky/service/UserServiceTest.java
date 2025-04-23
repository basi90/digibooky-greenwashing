package com.greenwashing.digibooky.service;

import com.greenwashing.digibooky.domain.User;
import com.greenwashing.digibooky.domain.UserRole;
import com.greenwashing.digibooky.infrastructure.UserRepository;
import com.greenwashing.digibooky.service.DTOs.UserInputDTO;
import com.greenwashing.digibooky.service.DTOs.UserOutputDTO;
import com.greenwashing.digibooky.service.mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static final UserRole MEMBER = UserRole.MEMBER;
    private static final UserRole LIBRARIAN = UserRole.LIBRARIAN;
    private static final UserRole ADMIN = UserRole.ADMIN;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void whenGetAllUsers_thenReturnDTOList() {

        User user1 = new User(MEMBER,"ssn1","email1","lastname1","city1","pass1");
        User user2 = new User(MEMBER,"ssn2","email2","lastname2","city2","pass2");

        //ReflectionTestUtils.setField(user1, "id", 1); INCREDIBLY USEFUL!!!!!! NO SETTERS NEEDED
        //ReflectionTestUtils.setField(user2, "id", 2); INCREDIBLY USEFUL!!!!!! NO SETTERS NEEDED

        UserOutputDTO userDTO1 = new UserOutputDTO(1, MEMBER , "email1","name1",
                "lastname1","street1",1,"city1","postal1");

        UserOutputDTO userDTO2 = new UserOutputDTO(2, MEMBER,"email2","name2",
                "lastname2","street2",2,"city2","postal2");

        when(userRepository.getAll()).thenReturn(List.of(user1, user2));
        when(userMapper.userToOutputDTO(user1)).thenReturn(userDTO1);
        when(userMapper.userToOutputDTO(user2)).thenReturn(userDTO2);

        List<UserOutputDTO> result = userService.viewMembersAsAdmin();

        verify(userMapper).userToOutputDTO(user1);
        verify(userMapper).userToOutputDTO(user2);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(userDTO1, userDTO2);
    }

    @Test
    void whenRegisterAsMember_thenReturnUserOutputDTOWithRoleMember() {
        //SETUP payload that determines outcomes
        UserInputDTO payload = new UserInputDTO(MEMBER,"ssn","a@b.co","name","lastname",
                "street",1,"city","postalcode","password");

        //expected outcomes
        User user = new User(MEMBER,"ssn","a@b.co","name","lastname","password");

        UserOutputDTO userOutputDTO = new UserOutputDTO(1, MEMBER,"a@b.co","name",
                "lastname","street",1,"city","postalcode");

        // "set" expected outcomes
        when(userMapper.inputDTOtoUser(payload)).thenReturn(user);
        when(userMapper.userToOutputDTO(user)).thenReturn(userOutputDTO);
        //create result object to asserts against --> aka this is the method this test is testing
        UserOutputDTO result = userService.registerAsMember(payload);
        //verify the correct methods were called
        verify(userMapper).inputDTOtoUser(payload);
        verify(userRepository).save(user);
        verify(userMapper).userToOutputDTO(user);
        //assert that your result is equal to the expected outcomeDTO
        assertThat(result).isEqualTo(userOutputDTO);
    }

    @Test
    void whenRegisterLibrarian_thenReturnUserOutputDTOWithRoleLibrarian() {

        UserInputDTO payload = new UserInputDTO(LIBRARIAN,"ssn","a@b.co","name","lastname",
                "street",1,"city","postalcode","password");

        User librarianUser = new User(LIBRARIAN,"ssn","a@b.co","name","lastname","password");

        UserOutputDTO userOutputDTO = new UserOutputDTO(1, LIBRARIAN,"a@b.co","name",
                "lastname","street",1,"city","postalcode");

        when(userMapper.inputDTOtoUser(payload)).thenReturn(librarianUser);
        when(userMapper.userToOutputDTO(librarianUser)).thenReturn(userOutputDTO);

        UserOutputDTO result = userService.registerLibrarian(payload);

        verify(userMapper).inputDTOtoUser(payload);
        verify(userRepository).save(librarianUser);
        verify(userMapper).userToOutputDTO(librarianUser);

        assertThat(result).isEqualTo(userOutputDTO);
    }

    @Test
    void whenRegisterAdmin_thenReturnUserOutputDTOWithRoleAdmin() {

        UserInputDTO payload = new UserInputDTO(ADMIN,"ssn","a@b.co","name","lastname",
                "street",1,"city","postalcode","password");

        User adminUser = new User(ADMIN,"ssn","a@b.co","name","lastname","password");

        UserOutputDTO userOutputDTO = new UserOutputDTO(1, ADMIN,"a@b.co","name",
                "lastname","street",1,"city","postalcode");

        when(userMapper.inputDTOtoUser(payload)).thenReturn(adminUser);
        when(userMapper.userToOutputDTO(adminUser)).thenReturn(userOutputDTO);

        UserOutputDTO result = userService.registerAdmin(payload);

        verify(userMapper).inputDTOtoUser(payload);
        verify(userRepository).save(adminUser);
        verify(userMapper).userToOutputDTO(adminUser);

        assertThat(result).isEqualTo(userOutputDTO);
    }

    @Test
    void whenRegisterMemberWithIncorrectPayloadRole_thenThrowException() {
        //SETUP invalid payload that causes exception
        UserInputDTO invalidPayload1 = new UserInputDTO(ADMIN,"ssn","x@y.z","name","lastname",
                "street",1,"city","postalcode","password");
        UserInputDTO invalidPayload2 = new UserInputDTO(LIBRARIAN,"ssn","x@y.z","name","lastname",
                "street",1,"city","postalcode","password");

        assertThatThrownBy(() -> userService.registerAsMember(invalidPayload1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> userService.registerAsMember(invalidPayload2)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenRegisterLibrarianWithIncorrectPayloadRole_thenThrowException() {
        UserInputDTO invalidPayload1 = new UserInputDTO(ADMIN,"ssn","x@y.z","name","lastname",
                "street",1,"city","postalcode","password");
        UserInputDTO invalidPayload2 = new UserInputDTO(MEMBER,"ssn","x@y.z","name","lastname",
                "street",1,"city","postalcode","password");

        assertThatThrownBy(() -> userService.registerLibrarian(invalidPayload1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> userService.registerLibrarian(invalidPayload2)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenRegisterAdminWithIncorrectPayloadRole_thenThrowException() {
        UserInputDTO invalidPayload1 = new UserInputDTO(MEMBER,"ssn","x@y.z","name","lastname",
                "street",1,"city","postalcode","password");
        UserInputDTO invalidPayload2 = new UserInputDTO(LIBRARIAN,"ssn","x@y.z","name","lastname",
                "street",1,"city","postalcode","password");

        assertThatThrownBy(() -> userService.registerAdmin(invalidPayload1)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> userService.registerAdmin(invalidPayload2)).isInstanceOf(IllegalArgumentException.class);
    }


}
