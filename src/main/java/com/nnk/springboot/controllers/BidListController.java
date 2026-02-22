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

import com.nnk.springboot.dto.bidlist.BidListDTO;
import com.nnk.springboot.dto.bidlist.CreateBidListDTO;
import com.nnk.springboot.service.BidListService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Contrôleur CRUD pour les BidList.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/bidList")
public class BidListController {

    private final BidListService bidListService;

    /**
     * Affiche la liste des bidlists.
     */
    @GetMapping("/list")
    public String home(Model model) {
        List<BidListDTO> blList = bidListService.getAllBidList();
        model.addAttribute("bidLists", blList);
        return "bidList/list";
    }

    /**
     * Affiche le formulaire de création d'une bidlist.
     */
    @GetMapping("/add")
    public String addBidForm(Model model) {
        model.addAttribute("bidList", CreateBidListDTO.builder().build());
        return "bidList/add";
    }

    /**
     * Valide et enregistre une nouvelle bidlist.
     */
    @PostMapping("/validate")
    public String validate(
            @Valid @ModelAttribute("bidList") CreateBidListDTO createBidListDTO,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "bidList/add";
        }

        bidListService.addBidList(createBidListDTO);
        return "redirect:/bidList/list";
    }

    /**
     * Affiche le formulaire de modification d'une bidlist.
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id,
            Model model) {
        BidListDTO bl = bidListService.getBidListById(id);
        model.addAttribute("bidList", bl);
        return "bidList/update";
    }

    /**
     * Valide et applique la modification d'une bidlist.
     */
    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id,
            @Valid @ModelAttribute("bidList") BidListDTO bl,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "bidList/update";
        }

        bidListService.updateBidList(id, bl);
        return "redirect:/bidList/list";
    }

    /**
     * Supprime une bidlist par son identifiant.
     */
    @PostMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        bidListService.deleteBidList(id);
        return "redirect:/bidList/list";
    }
}
