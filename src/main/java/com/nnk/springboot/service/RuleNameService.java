package com.nnk.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.rulename.CreateRuleNameDTO;
import com.nnk.springboot.dto.rulename.RuleNameDTO;
import com.nnk.springboot.repositories.RuleNameRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service de gestion des RuleName.
 */
@Service
@RequiredArgsConstructor
public class RuleNameService implements IRuleNameService {

    private final RuleNameRepository ruleNameRepository;

    /**
     * Retourne la liste de toutes les règles.
     *
     * @return liste des règles
     */
    @Transactional(readOnly = true)
    public List<RuleNameDTO> getAllRuleNames() {
        return ruleNameRepository.findAll().stream()
                .map(r -> RuleNameDTO.builder()
                        .id(r.getId())
                        .name(r.getName())
                        .description(r.getDescription())
                        .json(r.getJson())
                        .template(r.getTemplate())
                        .sqlStr(r.getSqlStr())
                        .sqlPart(r.getSqlPart())
                        .build())
                .toList();
    }

    /**
     * Crée et persiste une nouvelle règle.
     *
     * @param dto données de la règle à créer
     */
    @Transactional
    public void addRuleName(CreateRuleNameDTO dto) {
        RuleName ruleName = RuleName.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .json(dto.getJson())
                .template(dto.getTemplate())
                .sqlStr(dto.getSqlStr())
                .sqlPart(dto.getSqlPart())
                .build();
        ruleNameRepository.save(ruleName);
    }

    /**
     * Retourne une règle par son identifiant.
     *
     * @param id identifiant de la règle
     * @return la règle correspondante
     * @throws IllegalArgumentException si l'identifiant est introuvable
     */
    @Transactional(readOnly = true)
    public RuleNameDTO getRuleNameById(Integer id) {
        return ruleNameRepository.findById(id)
                .map(r -> RuleNameDTO.builder()
                        .id(r.getId())
                        .name(r.getName())
                        .description(r.getDescription())
                        .json(r.getJson())
                        .template(r.getTemplate())
                        .sqlStr(r.getSqlStr())
                        .sqlPart(r.getSqlPart())
                        .build())
                .orElseThrow(() -> new IllegalArgumentException("RuleName not found with id: " + id));
    }

    /**
     * Met à jour une règle existante.
     *
     * @param id  identifiant de la règle à modifier
     * @param dto nouvelles données
     * @throws IllegalArgumentException si l'identifiant est introuvable
     */
    @Transactional
    public void updateRuleName(Integer id, RuleNameDTO dto) {
        ruleNameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RuleName not found with id: " + id));

        RuleName ruleName = RuleName.builder()
                .id(id)
                .name(dto.getName())
                .description(dto.getDescription())
                .json(dto.getJson())
                .template(dto.getTemplate())
                .sqlStr(dto.getSqlStr())
                .sqlPart(dto.getSqlPart())
                .build();
        ruleNameRepository.save(ruleName);
    }

    /**
     * Supprime une règle par son identifiant.
     *
     * @param id identifiant de la règle à supprimer
     * @throws IllegalArgumentException si l'identifiant est introuvable
     */
    @Transactional
    public void deleteRuleName(Integer id) {
        if (!ruleNameRepository.existsById(id))
            throw new IllegalArgumentException("RuleName not found with id: " + id);

        ruleNameRepository.deleteById(id);
    }
}
