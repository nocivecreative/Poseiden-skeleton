package com.nnk.springboot.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor

public class AddBidListDTO {

    private final String account;
    private final String type;
    private final Double bidQuantity;

}
