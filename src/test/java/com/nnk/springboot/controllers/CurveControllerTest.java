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
import com.nnk.springboot.dto.curvepoint.CurvePointDTO;
import com.nnk.springboot.security.CustomUserDetailsService;
import com.nnk.springboot.service.ICurvePointService;

@WebMvcTest(CurveController.class)
@Import(SpringSecurityConfig.class)
class CurveControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private ICurvePointService curvePointService;

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
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    // --- Scénarios authentifiés (rôle USER) ---

    @Test
    @WithMockUser(roles = "USER")
    void getList_shouldReturn200() throws Exception {
        // Arrange
        when(curvePointService.getAllCurvePoint()).thenReturn(List.of());
        // Act & Assert
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void addCurvePointForm_shouldReturn200() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void validate_withValidData_shouldRedirect() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                .with(csrf())
                .param("curveId", "1")
                .param("term", "1.0")
                .param("value", "2.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void validate_withInvalidData_shouldReturnForm() throws Exception {
        // curveId manquant → @NotNull échoue
        mockMvc.perform(post("/curvePoint/validate")
                .with(csrf())
                .param("term", "1.0")
                .param("value", "2.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser(roles = "USER")
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
    @WithMockUser(roles = "USER")
    void updateCurvePoint_withValidData_shouldRedirect() throws Exception {
        mockMvc.perform(post("/curvePoint/update/1")
                .with(csrf())
                .param("curveId", "2")
                .param("term", "3.0")
                .param("value", "4.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateCurvePoint_withInvalidData_shouldReturnForm() throws Exception {
        // curveId manquant → @NotNull échoue
        mockMvc.perform(post("/curvePoint/update/1")
                .with(csrf())
                .param("term", "3.0")
                .param("value", "4.0"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteCurvePoint_shouldRedirect() throws Exception {
        mockMvc.perform(post("/curvePoint/delete/1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }
}
