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

import com.nnk.springboot.dto.bidlist.BidListDTO;
import com.nnk.springboot.service.BidListService;

@ExtendWith(MockitoExtension.class)
class BidListControllerTest {

    @Mock
    private BidListService bidListService;

    @InjectMocks
    private BidListController bidListController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        mockMvc = MockMvcBuilders.standaloneSetup(bidListController)
                .setValidator(validator)
                .build();
    }

    @Test
    void getList_shouldReturn200() throws Exception {
        // Arrange
        when(bidListService.getAllBidList()).thenReturn(List.of());
        // Act & Assert
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"));
    }

    @Test
    void addBidForm_shouldReturn200() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    void validate_withValidData_shouldRedirect() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/bidList/validate")
                .param("account", "Test Account")
                .param("type", "Test Type")
                .param("bidQuantity", "10.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    void validate_withInvalidData_shouldReturnForm() throws Exception {
        // Act & Assert (account vide → @NotBlank échoue)
        mockMvc.perform(post("/bidList/validate")
                .param("account", "")
                .param("type", "Type")
                .param("bidQuantity", "10.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
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
    void updateBid_withValidData_shouldRedirect() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/bidList/update/1")
                .param("account", "Updated")
                .param("type", "Type")
                .param("bidQuantity", "20.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    void updateBid_withInvalidData_shouldReturnForm() throws Exception {
        // Act & Assert (account vide → @NotBlank échoue)
        mockMvc.perform(post("/bidList/update/1")
                .param("account", "")
                .param("type", "Type")
                .param("bidQuantity", "20.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }

    @Test
    void deleteBid_shouldRedirect() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }
}
