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

import com.nnk.springboot.dto.curvepoint.CurvePointDTO;
import com.nnk.springboot.service.CurvePointService;

@ExtendWith(MockitoExtension.class)
class CurveControllerTest {

    @Mock
    private CurvePointService curvePointService;

    @InjectMocks
    private CurveController curveController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        mockMvc = MockMvcBuilders.standaloneSetup(curveController)
                .setValidator(validator)
                .build();
    }

    @Test
    void getList_shouldReturn200() throws Exception {
        // Arrange
        when(curvePointService.getAllCurvePoint()).thenReturn(List.of());
        // Act & Assert
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"));
    }

    @Test
    void addCurvePointForm_shouldReturn200() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    void validate_withValidData_shouldRedirect() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/curvePoint/validate")
                .param("curveId", "1")
                .param("term", "1.0")
                .param("value", "2.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    void validate_withInvalidData_shouldReturnForm() throws Exception {
        // Act & Assert (curveId manquant → @NotNull échoue)
        mockMvc.perform(post("/curvePoint/validate")
                .param("term", "1.0")
                .param("value", "2.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    void showUpdateForm_shouldReturn200() throws Exception {
        // Arrange
        when(curvePointService.getCurvePointById(1)).thenReturn(CurvePointDTO.builder()
                .id(1).curveId(1).term(1.0).value(2.0).build());
        // Act & Assert
        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    void updateCurvePoint_withValidData_shouldRedirect() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/curvePoint/update/1")
                .param("curveId", "2")
                .param("term", "3.0")
                .param("value", "4.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    void updateCurvePoint_withInvalidData_shouldReturnForm() throws Exception {
        // Act & Assert (curveId manquant → @NotNull échoue)
        mockMvc.perform(post("/curvePoint/update/1")
                .param("term", "3.0")
                .param("value", "4.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    void deleteCurvePoint_shouldRedirect() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }
}
