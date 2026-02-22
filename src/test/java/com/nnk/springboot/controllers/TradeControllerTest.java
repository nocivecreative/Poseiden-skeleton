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
import com.nnk.springboot.dto.trade.TradeDTO;
import com.nnk.springboot.security.CustomUserDetailsService;
import com.nnk.springboot.service.ITradeService;

@WebMvcTest(TradeController.class)
@Import(SpringSecurityConfig.class)
class TradeControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private ITradeService tradeService;

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
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    // --- Scénarios authentifiés (rôle USER) ---

    @Test
    @WithMockUser(roles = "USER")
    void getList_shouldReturn200() throws Exception {
        // Arrange
        when(tradeService.getAllTrades()).thenReturn(List.of());
        // Act & Assert
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void addTradeForm_shouldReturn200() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void validate_withValidData_shouldRedirect() throws Exception {
        mockMvc.perform(post("/trade/validate")
                .with(csrf())
                .param("account", "Test Account")
                .param("type", "Test Type")
                .param("buyQuantity", "10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void validate_withInvalidData_shouldReturnForm() throws Exception {
        // account vide → @NotBlank échoue
        mockMvc.perform(post("/trade/validate")
                .with(csrf())
                .param("account", "")
                .param("type", "Type")
                .param("buyQuantity", "10.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void showUpdateForm_shouldReturn200() throws Exception {
        // Arrange
        when(tradeService.getTradeById(1)).thenReturn(TradeDTO.builder()
                .tradeId(1).account("Account").type("Type").buyQuantity(10.0).build());
        // Act & Assert
        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateTrade_withValidData_shouldRedirect() throws Exception {
        mockMvc.perform(post("/trade/update/1")
                .with(csrf())
                .param("account", "Updated")
                .param("type", "Type")
                .param("buyQuantity", "20.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateTrade_withInvalidData_shouldReturnForm() throws Exception {
        // account vide → @NotBlank échoue
        mockMvc.perform(post("/trade/update/1")
                .with(csrf())
                .param("account", "")
                .param("type", "Type")
                .param("buyQuantity", "20.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteTrade_shouldRedirect() throws Exception {
        mockMvc.perform(post("/trade/delete/1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }
}
