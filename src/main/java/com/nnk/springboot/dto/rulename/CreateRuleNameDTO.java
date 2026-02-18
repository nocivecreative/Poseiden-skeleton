package com.nnk.springboot.dto.rulename;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateRuleNameDTO {

    @NotBlank(message = "Name is mandatory")
    private final String name;

    @NotBlank(message = "Description is mandatory")
    private final String description;

    @NotBlank(message = "Json is mandatory")
    private final String json;

    @NotBlank(message = "Template is mandatory")
    private final String template;

    @NotBlank(message = "SQL is mandatory")
    private final String sqlStr;

    @NotBlank(message = "SQL Part is mandatory")
    private final String sqlPart;
}
