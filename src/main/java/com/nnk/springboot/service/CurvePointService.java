package com.nnk.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.curvepoint.CreateCurvePointDTO;
import com.nnk.springboot.dto.curvepoint.CurvePointDTO;
import com.nnk.springboot.repositories.CurvePointRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service de gestion des CurvePoint.
 */
@Service
@RequiredArgsConstructor
public class CurvePointService implements ICurvePointService {

    private final CurvePointRepository curvePointRepository;

    /**
     * Retourne la liste de tous les points de courbe.
     *
     * @return liste des points de courbe
     */
    @Transactional(readOnly = true)
    public List<CurvePointDTO> getAllCurvePoint() {
        return curvePointRepository
                .findAll()
                .stream()
                .map(cp -> CurvePointDTO.builder()
                        .id(cp.getId())
                        .curveId(cp.getCurveId())
                        .term(cp.getTerm())
                        .value(cp.getValue())
                        .build())
                .toList();
    }

    /**
     * Crée et persiste un nouveau point de courbe.
     *
     * @param createCurvePointDTO données du point de courbe à créer
     */
    @Transactional
    public void addCurvePoint(CreateCurvePointDTO createCurvePointDTO) {
        CurvePoint cp = CurvePoint.builder()
                .curveId(createCurvePointDTO.getCurveId())
                .term(createCurvePointDTO.getTerm())
                .value(createCurvePointDTO.getValue())
                .build();
        curvePointRepository.save(cp);
    }

    /**
     * Retourne un point de courbe par son identifiant.
     *
     * @param id identifiant du point de courbe
     * @return le point de courbe correspondant
     * @throws IllegalArgumentException si l'identifiant est introuvable
     */
    @Transactional(readOnly = true)
    public CurvePointDTO getCurvePointById(Integer id) {
        return curvePointRepository.findById(id)
                .map(cp -> CurvePointDTO.builder()
                        .id(cp.getId())
                        .curveId(cp.getCurveId())
                        .term(cp.getTerm())
                        .value(cp.getValue())
                        .build())
                .orElseThrow(() -> new IllegalArgumentException("CurvePoint not found with id: " + id));
    }

    /**
     * Met à jour un point de courbe existant.
     *
     * @param id    identifiant du point de courbe à modifier
     * @param cpDTO nouvelles données
     * @throws IllegalArgumentException si l'identifiant est introuvable
     */
    @Transactional
    public void updateCurvePoint(Integer id, CurvePointDTO cpDTO) {
        curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CurvePoint not found with id: " + id));

        CurvePoint cp = CurvePoint.builder()
                .id(cpDTO.getId())
                .curveId(cpDTO.getCurveId())
                .term(cpDTO.getTerm())
                .value(cpDTO.getValue())
                .build();

        curvePointRepository.save(cp);
    }

    /**
     * Supprime un point de courbe par son identifiant.
     *
     * @param id identifiant du point de courbe à supprimer
     * @throws IllegalArgumentException si l'identifiant est introuvable
     */
    @Transactional
    public void deleteCurvePoint(Integer id) {
        if (!curvePointRepository.existsById(id))
            throw new IllegalArgumentException("CurvePoint not found with id: " + id);

        curvePointRepository.deleteById(id);
    }
}
