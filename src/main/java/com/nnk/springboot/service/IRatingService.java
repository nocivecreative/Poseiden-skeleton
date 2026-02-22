package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.rating.CreateRatingDTO;
import com.nnk.springboot.dto.rating.RatingDTO;

/**
 * Contrat métier pour la gestion des Rating.
 */
public interface IRatingService {

    List<RatingDTO> getAllRatings();

    RatingDTO getRatingById(Integer id);

    void addRating(CreateRatingDTO dto);

    void updateRating(Integer id, RatingDTO dto);

    void deleteRating(Integer id);
}
