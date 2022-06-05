package com.diploma.service;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@Builder
public class SearchParameters {
    @Size(min = 1, max = 255, message
            = "Name keyword must be between 1 and 255 characters")
    private String nameKeyword;
    @Size(min = 1, max = 255, message
            = "Surname keyword must be between 1 and 255 characters")
    private String surnameKeyword;
    @Min(value = 0, message = "Offset should not be less than 0")
    @Builder.Default
    private Integer offset = 0;
    @Min(value = 3, message = "Limit should not be less than 3")
    @Max(value = 200, message = "Limit should not be more than 200")
    @Builder.Default
    private Integer limit = 20;
}
