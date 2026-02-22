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

import com.nnk.springboot.dto.trade.CreateTradeDTO;
import com.nnk.springboot.dto.trade.TradeDTO;
import com.nnk.springboot.service.TradeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Contrôleur CRUD pour les Trade.
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/trade")
public class TradeController {

    private final TradeService tradeService;

    /**
     * Affiche la liste des trades.
     */
    @GetMapping("/list")
    public String home(Model model) {
        List<TradeDTO> trades = tradeService.getAllTrades();
        model.addAttribute("trades", trades);
        return "trade/list";
    }

    /**
     * Affiche le formulaire de création d'un trade.
     */
    @GetMapping("/add")
    public String addTradeForm(Model model) {
        model.addAttribute("trade", CreateTradeDTO.builder().build());
        return "trade/add";
    }

    /**
     * Valide et enregistre un nouveau trade.
     */
    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("trade") CreateTradeDTO createTradeDTO,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "trade/add";
        }

        tradeService.addTrade(createTradeDTO);
        return "redirect:/trade/list";
    }

    /**
     * Affiche le formulaire de modification d'un trade.
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        TradeDTO trade = tradeService.getTradeById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    /**
     * Valide et applique la modification d'un trade.
     */
    @PostMapping("/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id,
            @Valid @ModelAttribute("trade") TradeDTO trade,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "trade/update";
        }

        tradeService.updateTrade(id, trade);
        return "redirect:/trade/list";
    }

    /**
     * Supprime un trade par son identifiant.
     */
    @PostMapping("/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        tradeService.deleteTrade(id);
        return "redirect:/trade/list";
    }
}
