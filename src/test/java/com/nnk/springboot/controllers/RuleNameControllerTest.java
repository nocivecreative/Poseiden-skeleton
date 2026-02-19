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

import com.nnk.springboot.dto.rulename.RuleNameDTO;
import com.nnk.springboot.service.RuleNameService;

@ExtendWith(MockitoExtension.class)
class RuleNameControllerTest {

    @Mock
    private RuleNameService ruleNameService;

    @InjectMocks
    private RuleNameController ruleNameController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
        mockMvc = MockMvcBuilders.standaloneSetup(ruleNameController)
                .setValidator(validator)
                .build();
    }

    @Test
    void getList_shouldReturn200() throws Exception {
        // Arrange
        when(ruleNameService.getAllRuleNames()).thenReturn(List.of());
        // Act & Assert
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"));
    }

    @Test
    void addRuleNameForm_shouldReturn200() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    void validate_withValidData_shouldRedirect() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/ruleName/validate")
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
    void validate_withInvalidData_shouldReturnForm() throws Exception {
        // Act & Assert (name vide → @NotBlank échoue)
        mockMvc.perform(post("/ruleName/validate")
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
    void updateRuleName_withValidData_shouldRedirect() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/ruleName/update/1")
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
    void updateRuleName_withInvalidData_shouldReturnForm() throws Exception {
        // Act & Assert (name vide → @NotBlank échoue)
        mockMvc.perform(post("/ruleName/update/1")
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
    void deleteRuleName_shouldRedirect() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/ruleName/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }
}
