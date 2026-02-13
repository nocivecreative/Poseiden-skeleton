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

import com.nnk.springboot.dto.curvepoint.CreateCurvePointDTO;
import com.nnk.springboot.dto.curvepoint.CurvePointDTO;
import com.nnk.springboot.dto.curvepoint.EditCurvePointDTO;
import com.nnk.springboot.service.CurvePointService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/curvePoint")
public class CurveController {

    private final CurvePointService curvePointService;

    @GetMapping("/list")
    public String home(Model model) {
        List<CurvePointDTO> cpList = curvePointService.getAllCurvePoint();
        model.addAttribute("curvePoints", cpList);
        return "curvePoint/list";
    }

    @GetMapping("/add")
    public String addCurvePointForm(Model model) {
        model.addAttribute("curvePoint", new CreateCurvePointDTO());
        return "curvePoint/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("curvePoint") CreateCurvePointDTO createCurvePointDTO,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "curvePoint/add";
        }

        curvePointService.addCurvePoint(createCurvePointDTO);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        EditCurvePointDTO cp = curvePointService.getCurvePointById(id);
        model.addAttribute("curvePoint", cp);
        return "curvePoint/update";
    }

    @PostMapping("/update/{id}")
    public String updateCurvePoint(@PathVariable("id") Integer id,
            @Valid @ModelAttribute("curvePoint") EditCurvePointDTO cp,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "curvePoint/update";
        }

        curvePointService.updateCurvePoint(id, cp);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id, Model model) {
        curvePointService.deleteCurvePoint(id);
        return "redirect:/curvePoint/list";
    }
}
