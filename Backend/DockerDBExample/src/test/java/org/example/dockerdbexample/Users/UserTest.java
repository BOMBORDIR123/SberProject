package org.example.dockerdbexample.Users;

import org.example.dockerdbexample.dto.user.UserDto;
import org.example.dockerdbexample.dto.user.UserRegistrationDto;
import org.example.dockerdbexample.entity.User;
import org.example.dockerdbexample.exceptions.DifferentPassword;
import org.example.dockerdbexample.repository.UserRepository;
import org.example.dockerdbexample.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRegistrationDto userRegistrationDto;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPhoneNumber("1234567890");
        user.setPassword("password");
        user.setRoles(Set.of("USER"));

        userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setId(1L);
        userRegistrationDto.setFirstName("John");
        userRegistrationDto.setLastName("Doe");
        userRegistrationDto.setPhoneNumber("1234567890");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("password");
        userRegistrationDto.setRoles(Set.of("USER"));

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setPhoneNumber("1234567890");
        userDto.setPassword("password");
    }

    @Test
    void findByPhoneNumber_ShouldReturnUserDto_WhenUserExists() {
        when(userRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(user));
        UserDto foundUser = userService.findByPhoneNumber("1234567890");
        assertNotNull(foundUser);
        assertEquals("John", foundUser.getFirstName());
    }

    @Test
    void addUser_ShouldThrowException_WhenPasswordsDoNotMatch() {
        userRegistrationDto.setConfirmPassword("wrongpassword");
        assertThrows(DifferentPassword.class, () -> userService.addUser(userRegistrationDto));
    }

    @Test
    void addUser_ShouldSaveUser_WhenValid() {
        when(userRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserRegistrationDto savedUser = userService.addUser(userRegistrationDto);
        assertNotNull(savedUser);
        assertEquals("John", savedUser.getFirstName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void topUsers_ShouldReturnSortedUsers() {
        user.setFullBonus(100);
        User user2 = new User();
        user2.setFullBonus(200);
        when(userRepository.findAll()).thenReturn(List.of(user, user2));

        List<UserRegistrationDto> topUsers = userService.topUsers();
        assertEquals(2, topUsers.size());
    }

    @Test
    void updateUser_ShouldUpdateAndReturnUserDto() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDto updatedUser = userService.updateUser(userDto);
        assertNotNull(updatedUser);
        assertEquals("John", updatedUser.getFirstName());
    }
}