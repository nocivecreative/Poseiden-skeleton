package com.nnk.springboot.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UpdateBidListDTO {

    private final Integer bidListId;
    private final String account;
    private final String type;
    private final Double bidQuantity;

}
