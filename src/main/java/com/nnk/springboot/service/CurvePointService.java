package com.nnk.springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.curvepoint.CreateCurvePointDTO;
import com.nnk.springboot.dto.curvepoint.CurvePointDTO;
import com.nnk.springboot.repositories.CurvePointRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CurvePointService {

    private final CurvePointRepository curvePointRepository;

    @Transactional(readOnly = true)
    public List<CurvePointDTO> getAllCurvePoint() {
        return curvePointRepository
                .findAll()
                .stream()
                .map(cp -> new CurvePointDTO(
                        cp.getId(),
                        cp.getCurveId(),
                        cp.getTerm(),
                        cp.getValue()))
                .toList();
    }

    @Transactional
    public void addCurvePoint(CreateCurvePointDTO createCurvePointDTO) {
        CurvePoint cp = new CurvePoint(
                createCurvePointDTO.getCurveId(),
                createCurvePointDTO.getTerm(),
                createCurvePointDTO.getValue());
        curvePointRepository.save(cp);
    }

    @Transactional(readOnly = true)
    public CurvePointDTO getCurvePointById(Integer id) {
        return curvePointRepository.findById(id)
                .map(cp -> new CurvePointDTO(
                        cp.getId(),
                        cp.getCurveId(),
                        cp.getTerm(),
                        cp.getValue()))
                .orElseThrow(() -> new IllegalArgumentException("CurvePoint not found with id: " + id));
    }

    @Transactional
    public void updateCurvePoint(Integer id, CurvePointDTO cpDTO) {
        CurvePoint cp = curvePointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CurvePoint not found with id: " + id));

        cp.setCurveId(cpDTO.getCurveId());
        cp.setTerm(cpDTO.getTerm());
        cp.setValue(cpDTO.getValue());

        curvePointRepository.save(cp);
    }

    @Transactional
    public void deleteCurvePoint(Integer id) {
        if (!curvePointRepository.existsById(id))
            throw new IllegalArgumentException("CurvePoint not found with id: " + id);

        curvePointRepository.deleteById(id);
    }
}
