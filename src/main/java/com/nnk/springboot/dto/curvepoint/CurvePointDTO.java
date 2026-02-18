package com.nnk.springboot.dto.curvepoint;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CurvePointDTO {

    private final Integer id;

    @NotNull(message = "Curve ID must not be null")
    private final Integer curveId;

    @NotNull(message = "Term must not be null")
    private final Double term;

    @NotNull(message = "Value must not be null")
    private final Double value;
}
