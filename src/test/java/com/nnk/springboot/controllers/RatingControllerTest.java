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
import com.nnk.springboot.dto.rating.RatingDTO;
import com.nnk.springboot.security.CustomUserDetailsService;
import com.nnk.springboot.service.IRatingService;

@WebMvcTest(RatingController.class)
@Import(SpringSecurityConfig.class)
class RatingControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private IRatingService ratingService;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    // --- Contrôle d'accès ---

    @Test
    void getList_withoutAuth_shouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    // --- Scénarios authentifiés (rôle USER) ---

    @Test
    @WithMockUser(roles = "USER")
    void getList_shouldReturn200() throws Exception {
        // Arrange
        when(ratingService.getAllRatings()).thenReturn(List.of());
        // Act & Assert
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void addRatingForm_shouldReturn200() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void validate_withValidData_shouldRedirect() throws Exception {
        mockMvc.perform(post("/rating/validate")
                .with(csrf())
                .param("moodysRating", "Aaa")
                .param("sandPRating", "AAA")
                .param("fitchRating", "AAA")
                .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void validate_withInvalidData_shouldReturnForm() throws Exception {
        // moodysRating vide → @NotBlank échoue
        mockMvc.perform(post("/rating/validate")
                .with(csrf())
                .param("moodysRating", "")
                .param("sandPRating", "AAA")
                .param("fitchRating", "AAA")
                .param("orderNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void showUpdateForm_shouldReturn200() throws Exception {
        // Arrange
        when(ratingService.getRatingById(1)).thenReturn(RatingDTO.builder()
                .id(1).moodysRating("Aaa").sandPRating("AAA").fitchRating("AAA").orderNumber(1).build());
        // Act & Assert
        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateRating_withValidData_shouldRedirect() throws Exception {
        mockMvc.perform(post("/rating/update/1")
                .with(csrf())
                .param("moodysRating", "Bbb")
                .param("sandPRating", "BBB")
                .param("fitchRating", "BBB")
                .param("orderNumber", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateRating_withInvalidData_shouldReturnForm() throws Exception {
        // moodysRating vide → @NotBlank échoue
        mockMvc.perform(post("/rating/update/1")
                .with(csrf())
                .param("moodysRating", "")
                .param("sandPRating", "BBB")
                .param("fitchRating", "BBB")
                .param("orderNumber", "2"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteRating_shouldRedirect() throws Exception {
        mockMvc.perform(post("/rating/delete/1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }
}
