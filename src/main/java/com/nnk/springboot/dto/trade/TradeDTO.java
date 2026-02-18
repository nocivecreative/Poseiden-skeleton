package com.nnk.springboot.dto.trade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TradeDTO {

    private final Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    private final String account;

    @NotBlank(message = "Type is mandatory")
    private final String type;

    @NotNull(message = "Buy quantity must not be null")
    private final Double buyQuantity;
}
