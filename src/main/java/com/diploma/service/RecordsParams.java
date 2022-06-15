package com.diploma.service;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
public class RecordsParams {
    private Long userId;
    @Min(value = 0, message = "Offset should not be less than 0")
    @Builder.Default
    private Integer offset = 0;
    @Min(value = 3, message = "Limit should not be less than 3")
    @Max(value = 200, message = "Limit should not be more than 200")
    @Builder.Default
    private Integer limit = 20;
}
