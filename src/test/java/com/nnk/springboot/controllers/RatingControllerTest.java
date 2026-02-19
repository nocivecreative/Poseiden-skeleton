package com.nnk.springboot.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.nnk.springboot.dto.rating.RatingDTO;
import com.nnk.springboot.service.RatingService;

@ExtendWith(MockitoExtension.class)
class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController)
                .setValidator(validator)
                .build();
    }

    @Test
    void getList_shouldReturn200() throws Exception {
        // Arrange
        when(ratingService.getAllRatings()).thenReturn(List.of());
        // Act & Assert
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"));
    }

    @Test
    void addRatingForm_shouldReturn200() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    void validate_withValidData_shouldRedirect() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/rating/validate")
                .param("moodysRating", "Aaa")
                .param("sandPRating", "AAA")
                .param("fitchRating", "AAA")
                .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    void validate_withInvalidData_shouldReturnForm() throws Exception {
        // Act & Assert (moodysRating vide → @NotBlank échoue)
        mockMvc.perform(post("/rating/validate")
                .param("moodysRating", "")
                .param("sandPRating", "AAA")
                .param("fitchRating", "AAA")
                .param("orderNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
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
    void updateRating_withValidData_shouldRedirect() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/rating/update/1")
                .param("moodysRating", "Bbb")
                .param("sandPRating", "BBB")
                .param("fitchRating", "BBB")
                .param("orderNumber", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    void updateRating_withInvalidData_shouldReturnForm() throws Exception {
        // Act & Assert (moodysRating vide → @NotBlank échoue)
        mockMvc.perform(post("/rating/update/1")
                .param("moodysRating", "")
                .param("sandPRating", "BBB")
                .param("fitchRating", "BBB")
                .param("orderNumber", "2"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    void deleteRating_shouldRedirect() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }
}
