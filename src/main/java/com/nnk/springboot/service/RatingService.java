package com.nnk.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.rating.CreateRatingDTO;
import com.nnk.springboot.dto.rating.RatingDTO;
import com.nnk.springboot.repositories.RatingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    @Transactional(readOnly = true)
    public List<RatingDTO> getAllRatings() {
        return ratingRepository
                .findAll()
                .stream()
                .map(r -> new RatingDTO(
                        r.getId(),
                        r.getMoodysRating(),
                        r.getSandPRating(),
                        r.getFitchRating(),
                        r.getOrderNumber()))
                .toList();
    }

    @Transactional
    public void addRating(CreateRatingDTO dto) {
        Rating rating = new Rating();
        rating.setMoodysRating(dto.getMoodysRating());
        rating.setSandPRating(dto.getSandPRating());
        rating.setFitchRating(dto.getFitchRating());
        rating.setOrderNumber(dto.getOrderNumber());
        ratingRepository.save(rating);
    }

    @Transactional(readOnly = true)
    public RatingDTO getRatingById(Integer id) {
        return ratingRepository.findById(id)
                .map(r -> new RatingDTO(
                        r.getId(),
                        r.getMoodysRating(),
                        r.getSandPRating(),
                        r.getFitchRating(),
                        r.getOrderNumber()))
                .orElseThrow(() -> new IllegalArgumentException("Rating not found with id: " + id));
    }

    @Transactional
    public void updateRating(Integer id, RatingDTO dto) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found with id: " + id));

        rating.setMoodysRating(dto.getMoodysRating());
        rating.setSandPRating(dto.getSandPRating());
        rating.setFitchRating(dto.getFitchRating());
        rating.setOrderNumber(dto.getOrderNumber());

        ratingRepository.save(rating);
    }

    @Transactional
    public void deleteRating(Integer id) {
        if (!ratingRepository.existsById(id))
            throw new IllegalArgumentException("Rating not found with id: " + id);

        ratingRepository.deleteById(id);
    }
}
