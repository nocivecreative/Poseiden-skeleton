package com.nnk.springboot.dto.curvepoint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurvePointDTO {

    private Integer id;
    private Integer curveId;
    private Double term;
    private Double value;
}
