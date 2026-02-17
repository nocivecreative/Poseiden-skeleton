package com.nnk.springboot.dto.rating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRatingDTO {

    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    private Integer orderNumber;
}
