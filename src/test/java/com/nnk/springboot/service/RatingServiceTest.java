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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.rating.CreateRatingDTO;
import com.nnk.springboot.dto.rating.RatingDTO;
import com.nnk.springboot.repositories.RatingRepository;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    private final Rating entity = Rating.builder()
            .id(1).moodysRating("Aaa").sandPRating("AAA").fitchRating("AAA").orderNumber(1).build();

    @Test
    void addRating_shouldSaveEntity() {
        // Arrange
        CreateRatingDTO dto = CreateRatingDTO.builder()
                .moodysRating("Aaa").sandPRating("AAA").fitchRating("AAA").orderNumber(1).build();

        // Act
        ratingService.addRating(dto);

        // Assert
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void getRatingById_whenFound_shouldReturnDto() {
        // Arrange
        when(ratingRepository.findById(1)).thenReturn(Optional.of(entity));

        // Act
        RatingDTO result = ratingService.getRatingById(1);

        // Assert
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getMoodysRating()).isEqualTo("Aaa");
    }

    @Test
    void getRatingById_whenNotFound_shouldThrow() {
        // Arrange
        when(ratingRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> ratingService.getRatingById(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }

    @Test
    void gettAllRating_whenFound_shouldReturnListOfDto() {
        // Arrange
        when(ratingRepository.findAll()).thenReturn(List.of(entity));

        // Act
        List<RatingDTO> result = ratingService.getAllRatings();

        // Assert
        assertThat(result).isNotNull().hasSize(1);

    }

    @Test
    void updateRating_whenFound_shouldSaveUpdated() {
        // Arrange
        when(ratingRepository.findById(1)).thenReturn(Optional.of(entity));
        RatingDTO dto = RatingDTO.builder()
                .id(1).moodysRating("Bbb").sandPRating("BBB").fitchRating("BBB").orderNumber(2).build();

        // Act
        ratingService.updateRating(1, dto);

        // Assert
        verify(ratingRepository).save(any(Rating.class));
    }

    @Test
    void deleteRating_whenExists_shouldDeleteById() {
        // Arrange
        when(ratingRepository.existsById(1)).thenReturn(true);

        // Act
        ratingService.deleteRating(1);

        // Assert
        verify(ratingRepository).deleteById(1);
    }

    @Test
    void deleteRating_whenNotExists_shouldThrow() {
        // Arrange
        when(ratingRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> ratingService.deleteRating(99))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }
}
