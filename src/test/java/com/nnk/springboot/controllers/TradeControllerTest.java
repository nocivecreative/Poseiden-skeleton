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

import com.nnk.springboot.dto.trade.TradeDTO;
import com.nnk.springboot.service.TradeService;

@ExtendWith(MockitoExtension.class)
class TradeControllerTest {

    @Mock
    private TradeService tradeService;

    @InjectMocks
    private TradeController tradeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController)
                .setValidator(validator)
                .build();
    }

    @Test
    void getList_shouldReturn200() throws Exception {
        // Arrange
        when(tradeService.getAllTrades()).thenReturn(List.of());
        // Act & Assert
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"));
    }

    @Test
    void addTradeForm_shouldReturn200() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    void validate_withValidData_shouldRedirect() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/trade/validate")
                .param("account", "Test Account")
                .param("type", "Test Type")
                .param("buyQuantity", "10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    void validate_withInvalidData_shouldReturnForm() throws Exception {
        // Act & Assert (account vide → @NotBlank échoue)
        mockMvc.perform(post("/trade/validate")
                .param("account", "")
                .param("type", "Type")
                .param("buyQuantity", "10.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
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
    void updateTrade_withValidData_shouldRedirect() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/trade/update/1")
                .param("account", "Updated")
                .param("type", "Type")
                .param("buyQuantity", "20.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    void updateTrade_withInvalidData_shouldReturnForm() throws Exception {
        // Act & Assert (account vide → @NotBlank échoue)
        mockMvc.perform(post("/trade/update/1")
                .param("account", "")
                .param("type", "Type")
                .param("buyQuantity", "20.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @Test
    void deleteTrade_shouldRedirect() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }
}
