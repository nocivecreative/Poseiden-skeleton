package com.nnk.springboot.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setPassword("Test1234!");
        user.setFullname("Test User");
        user.setRole("USER");
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of(user));

        // Act
        List<User> result = userService.findAll();

        // Assert
        assertThat(result).hasSize(1).contains(user);
    }

    @Test
    void findById_whenFound_shouldReturnUser() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        User result = userService.findById(1);

        // Assert
        assertThat(result).isEqualTo(user);
    }

    @Test
    void findById_whenNotFound_shouldThrow() {
        // Arrange
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.findById(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }

    @Test
    void save_shouldEncodePasswordThenSave() {
        // Arrange
        when(passwordEncoder.encode("Test1234!")).thenReturn("encoded-password");

        // Act
        userService.save(user);

        // Assert
        assertThat(user.getPassword()).isEqualTo("encoded-password");
        verify(userRepository).save(user);
    }

    @Test
    void update_shouldSetIdEncodePasswordThenSave() {
        // Arrange
        when(passwordEncoder.encode("Test1234!")).thenReturn("encoded-password");

        // Act
        userService.update(1, user);

        // Assert
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getPassword()).isEqualTo("encoded-password");
        verify(userRepository).save(user);
    }

    @Test
    void delete_whenFound_shouldDeleteUser() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        userService.delete(1);

        // Assert
        verify(userRepository).delete(user);
    }

    @Test
    void delete_whenNotFound_shouldThrow() {
        // Arrange
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.delete(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }
}
