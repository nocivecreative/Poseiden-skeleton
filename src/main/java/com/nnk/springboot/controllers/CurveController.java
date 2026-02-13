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

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.BidList.BidListDTO;
import com.nnk.springboot.dto.curvepoint.CreateCurvePointDTO;
import com.nnk.springboot.dto.curvepoint.CurvePointDTO;
import com.nnk.springboot.dto.curvepoint.EditCurvePointDTO;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.service.CurvePointService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/curvePoint")
public class CurveController {

    private final CurvePointService curvePointService;

    @RequestMapping("/list")
    public String home(Model model) {

        List<CurvePointDTO> cpList = curvePointService.getAllCurvePoint();
        model.addAttribute("curvePoints", cpList);
        return "curvePoint/list";
    }

    @GetMapping("/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("curvePoint") CreateCurvePointDTO createCurvePointDTO,
            BindingResult result,
            Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
        curvePointService.addCurvePoint(createCurvePointDTO);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        EditCurvePointDTO cp = curvePointService.getCurvePointById(id);
        model.addAttribute("curvePoint", cp);
        return "curvePoint/update";
    }

    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") Integer id,
            @Valid @ModelAttribute("curvePoint") EditCurvePointDTO cp,
            BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return
        // Curve list
        curvePointService.updateCurvePoint(id, cp);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        curvePointService.deleteCurvePoint(id);
        return "redirect:/curvePoint/list";
    }
}
