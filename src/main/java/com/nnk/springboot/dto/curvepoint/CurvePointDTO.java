package com.nnk.springboot.dto.curvepoint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CurvePointDTO {
    private final Integer id;
    private final Integer curvePointId;
    private final Double term;
    private final Double value;
}
