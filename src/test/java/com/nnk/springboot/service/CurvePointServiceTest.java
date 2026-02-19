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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.curvepoint.CreateCurvePointDTO;
import com.nnk.springboot.dto.curvepoint.CurvePointDTO;
import com.nnk.springboot.repositories.CurvePointRepository;

@ExtendWith(MockitoExtension.class)
class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;

    private final CurvePoint entity = CurvePoint.builder()
            .id(1).curveId(1).term(1.0).value(2.0).build();

    @Test
    void addCurvePoint_shouldSaveEntity() {
        // Arrange
        CreateCurvePointDTO dto = CreateCurvePointDTO.builder()
                .curveId(1).term(1.0).value(2.0).build();

        // Act
        curvePointService.addCurvePoint(dto);

        // Assert
        verify(curvePointRepository).save(any(CurvePoint.class));
    }

    @Test
    void getCurvePointById_whenFound_shouldReturnDto() {
        // Arrange
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(entity));

        // Act
        CurvePointDTO result = curvePointService.getCurvePointById(1);

        // Assert
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getCurveId()).isEqualTo(1);
    }

    @Test
    void getCurvePointById_whenNotFound_shouldThrow() {
        // Arrange
        when(curvePointRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> curvePointService.getCurvePointById(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }

    @Test
    void gettAllCurvePoint_whenFound_shouldReturnListOfDto() {
        // Arrange
        when(curvePointRepository.findAll()).thenReturn(List.of(entity));

        // Act
        List<CurvePointDTO> result = curvePointService.getAllCurvePoint();

        // Assert
        assertThat(result).isNotNull().hasSize(1);

    }

    @Test
    void updateCurvePoint_whenFound_shouldSaveUpdated() {
        // Arrange
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(entity));
        CurvePointDTO dto = CurvePointDTO.builder()
                .id(1).curveId(2).term(3.0).value(4.0).build();

        // Act
        curvePointService.updateCurvePoint(1, dto);

        // Assert
        verify(curvePointRepository).save(any(CurvePoint.class));
    }

    @Test
    void deleteCurvePoint_whenExists_shouldDeleteById() {
        // Arrange
        when(curvePointRepository.existsById(1)).thenReturn(true);

        // Act
        curvePointService.deleteCurvePoint(1);

        // Assert
        verify(curvePointRepository).deleteById(1);
    }

    @Test
    void deleteCurvePoint_whenNotExists_shouldThrow() {
        // Arrange
        when(curvePointRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> curvePointService.deleteCurvePoint(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }
}
