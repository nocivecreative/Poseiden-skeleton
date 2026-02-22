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
import com.nnk.springboot.dto.bidlist.BidListDTO;
import com.nnk.springboot.security.CustomUserDetailsService;
import com.nnk.springboot.service.IBidListService;

@WebMvcTest(BidListController.class)
@Import(SpringSecurityConfig.class)
class BidListControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private IBidListService bidListService;

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
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    // --- Scénarios authentifiés (rôle USER) ---

    @Test
    @WithMockUser(roles = "USER")
    void getList_shouldReturn200() throws Exception {
        // Arrange
        when(bidListService.getAllBidList()).thenReturn(List.of());
        // Act & Assert
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void addBidForm_shouldReturn200() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void validate_withValidData_shouldRedirect() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                .with(csrf())
                .param("account", "Test Account")
                .param("type", "Test Type")
                .param("bidQuantity", "10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void validate_withInvalidData_shouldReturnForm() throws Exception {
        // account vide → @NotBlank échoue
        mockMvc.perform(post("/bidList/validate")
                .with(csrf())
                .param("account", "")
                .param("type", "Type")
                .param("bidQuantity", "10.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void showUpdateForm_shouldReturn200() throws Exception {
        // Arrange
        when(bidListService.getBidListById(1)).thenReturn(BidListDTO.builder()
                .bidListId(1).account("Account").type("Type").bidQuantity(10.0).build());
        // Act & Assert
        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateBid_withValidData_shouldRedirect() throws Exception {
        mockMvc.perform(post("/bidList/update/1")
                .with(csrf())
                .param("account", "Updated")
                .param("type", "Type")
                .param("bidQuantity", "20.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateBid_withInvalidData_shouldReturnForm() throws Exception {
        // account vide → @NotBlank échoue
        mockMvc.perform(post("/bidList/update/1")
                .with(csrf())
                .param("account", "")
                .param("type", "Type")
                .param("bidQuantity", "20.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteBid_shouldRedirect() throws Exception {
        mockMvc.perform(post("/bidList/delete/1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }
}
