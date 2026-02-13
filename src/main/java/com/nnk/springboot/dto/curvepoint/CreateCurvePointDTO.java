package com.nnk.springboot.dto.curvepoint;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCurvePointDTO {

    @NotNull(message = "Term is mandatory")
    private Double term;

    @NotNull(message = "Value is mandatory")
    private Double value;
}
