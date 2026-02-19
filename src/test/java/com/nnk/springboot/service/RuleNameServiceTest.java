package com.nnk.springboot.service;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.rulename.CreateRuleNameDTO;
import com.nnk.springboot.dto.rulename.RuleNameDTO;
import com.nnk.springboot.repositories.RuleNameRepository;

@ExtendWith(MockitoExtension.class)
class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;

    private final RuleName entity = RuleName.builder()
            .id(1).name("Rule").description("Desc").json("{}")
            .template("tpl").sqlStr("SELECT 1").sqlPart("WHERE 1=1").build();

    @Test
    void addRuleName_shouldSaveEntity() {
        // Arrange
        CreateRuleNameDTO dto = CreateRuleNameDTO.builder()
                .name("Rule").description("Desc").json("{}")
                .template("tpl").sqlStr("SELECT 1").sqlPart("WHERE 1=1").build();

        // Act
        ruleNameService.addRuleName(dto);

        // Assert
        verify(ruleNameRepository).save(any(RuleName.class));
    }

    @Test
    void getRuleNameById_whenFound_shouldReturnDto() {
        // Arrange
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(entity));

        // Act
        RuleNameDTO result = ruleNameService.getRuleNameById(1);

        // Assert
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Rule");
    }

    @Test
    void getRuleNameById_whenNotFound_shouldThrow() {
        // Arrange
        when(ruleNameRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> ruleNameService.getRuleNameById(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }

    @Test
    void gettAllRuleName_whenFound_shouldReturnListOfDto() {
        // Arrange
        when(ruleNameRepository.findAll()).thenReturn(List.of(entity));

        // Act
        List<RuleNameDTO> result = ruleNameService.getAllRuleNames();

        // Assert
        assertThat(result).isNotNull().hasSize(1);

    }

    @Test
    void updateRuleName_whenFound_shouldSaveUpdated() {
        // Arrange
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(entity));
        RuleNameDTO dto = RuleNameDTO.builder()
                .id(1).name("Updated").description("D").json("{}")
                .template("t").sqlStr("S").sqlPart("P").build();

        // Act
        ruleNameService.updateRuleName(1, dto);

        // Assert
        verify(ruleNameRepository).save(any(RuleName.class));
    }

    @Test
    void deleteRuleName_whenExists_shouldDeleteById() {
        // Arrange
        when(ruleNameRepository.existsById(1)).thenReturn(true);

        // Act
        ruleNameService.deleteRuleName(1);

        // Assert
        verify(ruleNameRepository).deleteById(1);
    }

    @Test
    void deleteRuleName_whenNotExists_shouldThrow() {
        // Arrange
        when(ruleNameRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> ruleNameService.deleteRuleName(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }
}
