package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.rulename.CreateRuleNameDTO;
import com.nnk.springboot.dto.rulename.RuleNameDTO;

/**
 * Contrat métier pour la gestion des RuleName.
 */
public interface IRuleNameService {

    List<RuleNameDTO> getAllRuleNames();

    RuleNameDTO getRuleNameById(Integer id);

    void addRuleName(CreateRuleNameDTO dto);

    void updateRuleName(Integer id, RuleNameDTO dto);

    void deleteRuleName(Integer id);
}
