package com.nnk.springboot.dto.curvepoint;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateCurvePointDTO {

    @NotNull(message = "Curve ID must not be null")
    private final Integer curveId;

    @NotNull(message = "Term must not be null")
    private final Double term;

    @NotNull(message = "Value must not be null")
    private final Double value;
}
