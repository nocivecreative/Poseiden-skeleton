package com.nnk.springboot.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setValidator(validator)
                .build();
    }

    @Test
    void getList_shouldReturn200() throws Exception {
        // Arrange
        when(userRepository.findAll()).thenReturn(List.of());
        // Act & Assert
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));
    }

    @Test
    void addUserForm_shouldReturn200() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    void validate_withValidUser_shouldRedirect() throws Exception {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(userRepository.findAll()).thenReturn(List.of());
        // Act & Assert (mot de passe conforme à la regex @Pattern)
        mockMvc.perform(post("/user/validate")
                .param("username", "testuser")
                .param("password", "Test1234!")
                .param("fullname", "Test User")
                .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    void validate_withWeakPassword_shouldReturnForm() throws Exception {
        // Act & Assert (password ne respecte pas @Pattern)
        mockMvc.perform(post("/user/validate")
                .param("username", "testuser")
                .param("password", "weak")
                .param("fullname", "Test User")
                .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    void showUpdateForm_shouldReturn200() throws Exception {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setPassword("Test1234!");
        user.setFullname("Test User");
        user.setRole("USER");
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        // Act & Assert
        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    void updateUser_withValidUser_shouldRedirect() throws Exception {
        // Arrange
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(userRepository.findAll()).thenReturn(List.of());
        // Act & Assert
        mockMvc.perform(post("/user/update/1")
                .param("username", "updated")
                .param("password", "NewPass1!")
                .param("fullname", "Updated User")
                .param("role", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    void updateUser_withWeakPassword_shouldReturnForm() throws Exception {
        // Act & Assert (password ne respecte pas @Pattern)
        mockMvc.perform(post("/user/update/1")
                .param("username", "updated")
                .param("password", "weak")
                .param("fullname", "Updated User")
                .param("role", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    void deleteUser_shouldRedirect() throws Exception {
        // Arrange
        User user = new User();
        user.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.findAll()).thenReturn(List.of());
        // Act & Assert
        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }
}
