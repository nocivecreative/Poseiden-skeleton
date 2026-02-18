package com.nnk.springboot.dto.rating;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateRatingDTO {

    @NotBlank(message = "MoodysRating is mandatory")
    private final String moodysRating;

    @NotBlank(message = "SandPRating is mandatory")
    private final String sandPRating;

    @NotBlank(message = "FitchRating is mandatory")
    private final String fitchRating;

    @NotNull(message = "Order number must not be null")
    private final Integer orderNumber;
}
