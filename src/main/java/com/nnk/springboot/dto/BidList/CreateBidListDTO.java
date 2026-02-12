package com.nnk.springboot.dto.BidList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateBidListDTO {

    private final String account;
    private final String type;
    private final Double bidQuantity;

}
