package com.nnk.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.rating.CreateRatingDTO;
import com.nnk.springboot.dto.rating.RatingDTO;
import com.nnk.springboot.repositories.RatingRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service de gestion des Rating.
 */
@Service
@RequiredArgsConstructor
public class RatingService implements IRatingService {

    private final RatingRepository ratingRepository;

    /**
     * Retourne la liste de tous les ratings.
     *
     * @return liste des ratings
     */
    @Transactional(readOnly = true)
    public List<RatingDTO> getAllRatings() {
        return ratingRepository.findAll().stream()
                .map(r -> RatingDTO.builder()
                        .id(r.getId())
                        .moodysRating(r.getMoodysRating())
                        .sandPRating(r.getSandPRating())
                        .fitchRating(r.getFitchRating())
                        .orderNumber(r.getOrderNumber())
                        .build())
                .toList();
    }

    /**
     * Crée et persiste un nouveau rating.
     *
     * @param dto données du rating à créer
     */
    @Transactional
    public void addRating(CreateRatingDTO dto) {
        Rating rating = Rating.builder()
                .moodysRating(dto.getMoodysRating())
                .sandPRating(dto.getSandPRating())
                .fitchRating(dto.getFitchRating())
                .orderNumber(dto.getOrderNumber())
                .build();
        ratingRepository.save(rating);
    }

    /**
     * Retourne un rating par son identifiant.
     *
     * @param id identifiant du rating
     * @return le rating correspondant
     * @throws IllegalArgumentException si l'identifiant est introuvable
     */
    @Transactional(readOnly = true)
    public RatingDTO getRatingById(Integer id) {
        return ratingRepository.findById(id)
                .map(r -> RatingDTO.builder()
                        .id(r.getId())
                        .moodysRating(r.getMoodysRating())
                        .sandPRating(r.getSandPRating())
                        .fitchRating(r.getFitchRating())
                        .orderNumber(r.getOrderNumber())
                        .build())
                .orElseThrow(() -> new IllegalArgumentException("Rating not found with id: " + id));
    }

    /**
     * Met à jour un rating existant.
     *
     * @param id  identifiant du rating à modifier
     * @param dto nouvelles données
     * @throws IllegalArgumentException si l'identifiant est introuvable
     */
    @Transactional
    public void updateRating(Integer id, RatingDTO dto) {
        ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating not found with id: " + id));

        Rating rating = Rating.builder()
                .id(id)
                .moodysRating(dto.getMoodysRating())
                .sandPRating(dto.getSandPRating())
                .fitchRating(dto.getFitchRating())
                .orderNumber(dto.getOrderNumber())
                .build();
        ratingRepository.save(rating);
    }

    /**
     * Supprime un rating par son identifiant.
     *
     * @param id identifiant du rating à supprimer
     * @throws IllegalArgumentException si l'identifiant est introuvable
     */
    @Transactional
    public void deleteRating(Integer id) {
        if (!ratingRepository.existsById(id))
            throw new IllegalArgumentException("Rating not found with id: " + id);

        ratingRepository.deleteById(id);
    }
}
