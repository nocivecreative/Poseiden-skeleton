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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.bidlist.BidListDTO;
import com.nnk.springboot.dto.bidlist.CreateBidListDTO;
import com.nnk.springboot.repositories.BidListRepository;

@ExtendWith(MockitoExtension.class)
class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    private final BidList entity = BidList.builder()
            .bidListId(1).account("Account").type("Type").bidQuantity(10.0).build();

    @Test
    void addBidList_shouldSaveEntity() {
        // Arrange
        CreateBidListDTO dto = CreateBidListDTO.builder()
                .account("Account").type("Type").bidQuantity(10.0).build();

        // Act
        bidListService.addBidList(dto);

        // Assert
        verify(bidListRepository).save(any(BidList.class));
    }

    @Test
    void getBidListById_whenFound_shouldReturnDto() {
        // Arrange
        when(bidListRepository.findById(1)).thenReturn(Optional.of(entity));

        // Act
        BidListDTO result = bidListService.getBidListById(1);

        // Assert
        assertThat(result.getBidListId()).isEqualTo(1);
        assertThat(result.getAccount()).isEqualTo("Account");
    }

    @Test
    void getBidListById_whenNotFound_shouldThrow() {
        // Arrange
        when(bidListRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> bidListService.getBidListById(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }

    @Test
    void getAllBidList_whenFound_shouldReturnListOfDto() {
        // Arrange
        when(bidListRepository.findAll()).thenReturn(List.of(entity));

        // Act
        List<BidListDTO> result = bidListService.getAllBidList();

        // Assert
        assertThat(result).isNotNull().hasSize(1);

    }

    @Test
    void updateBidList_whenFound_shouldSaveUpdated() {
        // Arrange
        when(bidListRepository.findById(1)).thenReturn(Optional.of(entity));
        BidListDTO dto = BidListDTO.builder()
                .bidListId(1).account("Updated").type("Type").bidQuantity(20.0).build();

        // Act
        bidListService.updateBidList(1, dto);

        // Assert
        verify(bidListRepository).save(any(BidList.class));
    }

    @Test
    void deleteBidList_whenExists_shouldDeleteById() {
        // Arrange
        when(bidListRepository.existsById(1)).thenReturn(true);

        // Act
        bidListService.deleteBidList(1);

        // Assert
        verify(bidListRepository).deleteById(1);
    }

    @Test
    void deleteBidList_whenNotExists_shouldThrow() {
        // Arrange
        when(bidListRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> bidListService.deleteBidList(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }
}
