package com.nnk.springboot.controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.request.AddBidListDTO;
import com.nnk.springboot.dto.response.BidListDTO;
import com.nnk.springboot.dto.response.UpdateBidListDTO;
import com.nnk.springboot.security.SecurityUser;
import com.nnk.springboot.service.BidListService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bidList")
public class BidListController {

    private final BidListService bidListService;

    @GetMapping("/list")
    public String home(Model model,
            @AuthenticationPrincipal SecurityUser currentUser) {
        // TODO: All ?

        List<BidListDTO> bl = bidListService.getAllBidList();
        model.addAttribute("bidLists", bl);
        return "bidList/list";
    }

    @GetMapping("/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/validate")
    public String validate(
            @Valid @ModelAttribute("bidList") AddBidListDTO addBidListDTO,
            BindingResult result,
            Model model) {
        // TODO: 3 champs seuleument ?

        bidListService.addBidList(addBidListDTO);

        return "redirect:/bidList/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id,
            Model model) {
        // TODO: rq idem add

        UpdateBidListDTO bl = bidListService.getBildListById(id);
        model.addAttribute("bidList", bl);
        return "bidList/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id,
            @Valid @ModelAttribute("BidList") UpdateBidListDTO bl,
            BindingResult result,
            Model model) {

        bidListService.updateBidList(id, bl);
        return "redirect:/bidList/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        bidListService.deleteBidList(id);

        return "redirect:/bidList/list";
    }
}
