package com.nnk.springboot.dto.bidlist;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateBidListDTO {

    @NotBlank(message = "Account is mandatory")
    private final String account;

    @NotBlank(message = "Type is mandatory")
    private final String type;

    @NotNull(message = "Bid Quantity must not be null")
    private final Double bidQuantity;

}
