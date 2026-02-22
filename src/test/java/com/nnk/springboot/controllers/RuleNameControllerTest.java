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
import com.nnk.springboot.dto.rulename.RuleNameDTO;
import com.nnk.springboot.security.CustomUserDetailsService;
import com.nnk.springboot.service.IRuleNameService;

@WebMvcTest(RuleNameController.class)
@Import(SpringSecurityConfig.class)
class RuleNameControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private IRuleNameService ruleNameService;

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
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    // --- Scénarios authentifiés (rôle USER) ---

    @Test
    @WithMockUser(roles = "USER")
    void getList_shouldReturn200() throws Exception {
        // Arrange
        when(ruleNameService.getAllRuleNames()).thenReturn(List.of());
        // Act & Assert
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void addRuleNameForm_shouldReturn200() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void validate_withValidData_shouldRedirect() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                .with(csrf())
                .param("name", "Rule")
                .param("description", "Desc")
                .param("json", "{}")
                .param("template", "tpl")
                .param("sqlStr", "SELECT 1")
                .param("sqlPart", "WHERE 1=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void validate_withInvalidData_shouldReturnForm() throws Exception {
        // name vide → @NotBlank échoue
        mockMvc.perform(post("/ruleName/validate")
                .with(csrf())
                .param("name", "")
                .param("description", "Desc")
                .param("json", "{}")
                .param("template", "tpl")
                .param("sqlStr", "SELECT 1")
                .param("sqlPart", "WHERE 1=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void showUpdateForm_shouldReturn200() throws Exception {
        // Arrange
        when(ruleNameService.getRuleNameById(1)).thenReturn(RuleNameDTO.builder()
                .id(1).name("Rule").description("Desc").json("{}")
                .template("tpl").sqlStr("SELECT 1").sqlPart("WHERE 1=1").build());
        // Act & Assert
        mockMvc.perform(get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateRuleName_withValidData_shouldRedirect() throws Exception {
        mockMvc.perform(post("/ruleName/update/1")
                .with(csrf())
                .param("name", "Updated")
                .param("description", "Desc")
                .param("json", "{}")
                .param("template", "tpl")
                .param("sqlStr", "SELECT 1")
                .param("sqlPart", "WHERE 1=1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateRuleName_withInvalidData_shouldReturnForm() throws Exception {
        // name vide → @NotBlank échoue
        mockMvc.perform(post("/ruleName/update/1")
                .with(csrf())
                .param("name", "")
                .param("description", "Desc")
                .param("json", "{}")
                .param("template", "tpl")
                .param("sqlStr", "SELECT 1")
                .param("sqlPart", "WHERE 1=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteRuleName_shouldRedirect() throws Exception {
        mockMvc.perform(post("/ruleName/delete/1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }
}
