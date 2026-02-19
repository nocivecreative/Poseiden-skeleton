package com.nnk.springboot.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.dto.rating.CreateRatingDTO;
import com.nnk.springboot.dto.rating.RatingDTO;
import com.nnk.springboot.service.RatingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Contrôleur CRUD pour les Rating.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;

    /**
     * Affiche la liste des ratings.
     */
    @GetMapping("/list")
    public String home(Model model) {
        List<RatingDTO> ratings = ratingService.getAllRatings();
        model.addAttribute("ratings", ratings);
        return "rating/list";
    }

    /**
     * Affiche le formulaire de création d'un rating.
     */
    @GetMapping("/add")
    public String addRatingForm(Model model) {
        model.addAttribute("rating", CreateRatingDTO.builder().build());
        return "rating/add";
    }

    /**
     * Valide et enregistre un nouveau rating.
     */
    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("rating") CreateRatingDTO createRatingDTO,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "rating/add";
        }

        ratingService.addRating(createRatingDTO);
        return "redirect:/rating/list";
    }

    /**
     * Affiche le formulaire de modification d'un rating.
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RatingDTO rating = ratingService.getRatingById(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    /**
     * Valide et applique la modification d'un rating.
     */
    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") Integer id,
            @Valid @ModelAttribute("rating") RatingDTO rating,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "rating/update";
        }

        ratingService.updateRating(id, rating);
        return "redirect:/rating/list";
    }

    /**
     * Supprime un rating par son identifiant.
     */
    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        ratingService.deleteRating(id);
        return "redirect:/rating/list";
    }
}
