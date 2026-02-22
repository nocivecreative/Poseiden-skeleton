package com.nnk.springboot.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nnk.springboot.config.SpringSecurityConfig;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.security.CustomUserDetailsService;
import com.nnk.springboot.service.IUserService;

@WebMvcTest(UserController.class)
@Import(SpringSecurityConfig.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private IUserService userService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    // --- Contrôle d'accès : /user/** exige le rôle ADMIN ---

    @Test
    void getList_withoutAuth_shouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getList_withUserRole_shouldBeForbidden() throws Exception {
        // Un utilisateur avec le rôle USER ne peut pas accéder à /user/**
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isForbidden());
    }

    // --- Scénarios authentifiés (rôle ADMIN) ---

    @Test
    @WithMockUser(roles = "ADMIN")
    void getList_withAdminRole_shouldReturn200() throws Exception {
        // Arrange
        when(userService.findAll()).thenReturn(List.of());
        // Act & Assert
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void addUserForm_shouldReturn200() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void validate_withValidUser_shouldRedirect() throws Exception {
        // password conforme au @Pattern requis
        mockMvc.perform(post("/user/validate")
                .with(csrf())
                .param("username", "testuser")
                .param("password", "Test1234!")
                .param("fullname", "Test User")
                .param("role", "USER"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void validate_withWeakPassword_shouldReturnForm() throws Exception {
        // password ne respecte pas le @Pattern → validation échoue
        mockMvc.perform(post("/user/validate")
                .with(csrf())
                .param("username", "testuser")
                .param("password", "weak")
                .param("fullname", "Test User")
                .param("role", "USER"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void showUpdateForm_shouldReturn200() throws Exception {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setPassword("Test1234!");
        user.setFullname("Test User");
        user.setRole("USER");
        when(userService.findById(1)).thenReturn(user);
        // Act & Assert
        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUser_withValidUser_shouldRedirect() throws Exception {
        mockMvc.perform(post("/user/update/1")
                .with(csrf())
                .param("username", "updated")
                .param("password", "NewPass1!")
                .param("fullname", "Updated User")
                .param("role", "ADMIN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUser_withWeakPassword_shouldReturnForm() throws Exception {
        // password ne respecte pas le @Pattern → validation échoue
        mockMvc.perform(post("/user/update/1")
                .with(csrf())
                .param("username", "updated")
                .param("password", "weak")
                .param("fullname", "Updated User")
                .param("role", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUser_shouldRedirect() throws Exception {
        mockMvc.perform(post("/user/delete/1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }
}
