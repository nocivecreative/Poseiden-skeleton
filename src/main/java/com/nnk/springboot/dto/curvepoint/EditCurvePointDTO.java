package com.nnk.springboot.dto.curvepoint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EditCurvePointDTO {
    private final Integer id;
    private final Double term;
    private final Double value;
}
