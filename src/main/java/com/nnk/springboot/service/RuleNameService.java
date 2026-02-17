package com.nnk.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.rulename.CreateRuleNameDTO;
import com.nnk.springboot.dto.rulename.RuleNameDTO;
import com.nnk.springboot.repositories.RuleNameRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    @Transactional(readOnly = true)
    public List<RuleNameDTO> getAllRuleNames() {
        return ruleNameRepository
                .findAll()
                .stream()
                .map(r -> new RuleNameDTO(
                        r.getId(),
                        r.getName(),
                        r.getDescription(),
                        r.getJson(),
                        r.getTemplate(),
                        r.getSqlStr(),
                        r.getSqlPart()))
                .toList();
    }

    @Transactional
    public void addRuleName(CreateRuleNameDTO dto) {
        RuleName ruleName = new RuleName();
        ruleName.setName(dto.getName());
        ruleName.setDescription(dto.getDescription());
        ruleName.setJson(dto.getJson());
        ruleName.setTemplate(dto.getTemplate());
        ruleName.setSqlStr(dto.getSqlStr());
        ruleName.setSqlPart(dto.getSqlPart());
        ruleNameRepository.save(ruleName);
    }

    @Transactional(readOnly = true)
    public RuleNameDTO getRuleNameById(Integer id) {
        return ruleNameRepository.findById(id)
                .map(r -> new RuleNameDTO(
                        r.getId(),
                        r.getName(),
                        r.getDescription(),
                        r.getJson(),
                        r.getTemplate(),
                        r.getSqlStr(),
                        r.getSqlPart()))
                .orElseThrow(() -> new IllegalArgumentException("RuleName not found with id: " + id));
    }

    @Transactional
    public void updateRuleName(Integer id, RuleNameDTO dto) {
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RuleName not found with id: " + id));

        ruleName.setName(dto.getName());
        ruleName.setDescription(dto.getDescription());
        ruleName.setJson(dto.getJson());
        ruleName.setTemplate(dto.getTemplate());
        ruleName.setSqlStr(dto.getSqlStr());
        ruleName.setSqlPart(dto.getSqlPart());

        ruleNameRepository.save(ruleName);
    }

    @Transactional
    public void deleteRuleName(Integer id) {
        if (!ruleNameRepository.existsById(id))
            throw new IllegalArgumentException("RuleName not found with id: " + id);

        ruleNameRepository.deleteById(id);
    }
}
