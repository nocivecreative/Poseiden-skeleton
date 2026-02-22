package com.nnk.springboot.service;

import java.util.List;

import com.nnk.springboot.dto.curvepoint.CreateCurvePointDTO;
import com.nnk.springboot.dto.curvepoint.CurvePointDTO;

/**
 * Contrat métier pour la gestion des CurvePoint.
 */
public interface ICurvePointService {

    List<CurvePointDTO> getAllCurvePoint();

    CurvePointDTO getCurvePointById(Integer id);

    void addCurvePoint(CreateCurvePointDTO createCurvePointDTO);

    void updateCurvePoint(Integer id, CurvePointDTO cpDTO);

    void deleteCurvePoint(Integer id);
}
