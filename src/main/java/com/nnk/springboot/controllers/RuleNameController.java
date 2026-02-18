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

import com.nnk.springboot.dto.rulename.CreateRuleNameDTO;
import com.nnk.springboot.dto.rulename.RuleNameDTO;
import com.nnk.springboot.service.RuleNameService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/ruleName")
public class RuleNameController {

    private final RuleNameService ruleNameService;

    @GetMapping("/list")
    public String home(Model model) {
        List<RuleNameDTO> ruleNames = ruleNameService.getAllRuleNames();
        model.addAttribute("ruleNames", ruleNames);
        return "ruleName/list";
    }

    @GetMapping("/add")
    public String addRuleNameForm(Model model) {
        model.addAttribute("ruleName", CreateRuleNameDTO.builder().build());
        return "ruleName/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid @ModelAttribute("ruleName") CreateRuleNameDTO createRuleNameDTO,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "ruleName/add";
        }

        ruleNameService.addRuleName(createRuleNameDTO);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleNameDTO ruleName = ruleNameService.getRuleNameById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id,
            @Valid @ModelAttribute("ruleName") RuleNameDTO ruleName,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "ruleName/update";
        }

        ruleNameService.updateRuleName(id, ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        ruleNameService.deleteRuleName(id);
        return "redirect:/ruleName/list";
    }
}
