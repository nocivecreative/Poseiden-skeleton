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

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.trade.CreateTradeDTO;
import com.nnk.springboot.dto.trade.TradeDTO;
import com.nnk.springboot.repositories.TradeRepository;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    private final Trade entity = Trade.builder()
            .tradeId(1).account("Account").type("Type").buyQuantity(10.0).build();

    @Test
    void addTrade_shouldSaveEntity() {
        // Arrange
        CreateTradeDTO dto = CreateTradeDTO.builder()
                .account("Account").type("Type").buyQuantity(10.0).build();

        // Act
        tradeService.addTrade(dto);

        // Assert
        verify(tradeRepository).save(any(Trade.class));
    }

    @Test
    void getTradeById_whenFound_shouldReturnDto() {
        // Arrange
        when(tradeRepository.findById(1)).thenReturn(Optional.of(entity));

        // Act
        TradeDTO result = tradeService.getTradeById(1);

        // Assert
        assertThat(result.getTradeId()).isEqualTo(1);
        assertThat(result.getAccount()).isEqualTo("Account");
    }

    @Test
    void getTradeById_whenNotFound_shouldThrow() {
        // Arrange
        when(tradeRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> tradeService.getTradeById(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }

    @Test
    void gettAllTrades_whenFound_shouldReturnListOfDto() {
        // Arrange
        when(tradeRepository.findAll()).thenReturn(List.of(entity));

        // Act
        List<TradeDTO> result = tradeService.getAllTrades();

        // Assert
        assertThat(result).isNotNull().hasSize(1);
    }

    @Test
    void updateTrade_whenFound_shouldSaveUpdated() {
        // Arrange
        when(tradeRepository.findById(1)).thenReturn(Optional.of(entity));
        TradeDTO dto = TradeDTO.builder()
                .tradeId(1).account("Updated").type("Type").buyQuantity(20.0).build();

        // Act
        tradeService.updateTrade(1, dto);

        // Assert
        verify(tradeRepository).save(any(Trade.class));
    }

    @Test
    void deleteTrade_whenExists_shouldDeleteById() {
        // Arrange
        when(tradeRepository.existsById(1)).thenReturn(true);

        // Act
        tradeService.deleteTrade(1);

        // Assert
        verify(tradeRepository).deleteById(1);
    }

    @Test
    void deleteTrade_whenNotExists_shouldThrow() {
        // Arrange
        when(tradeRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> tradeService.deleteTrade(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }
}
