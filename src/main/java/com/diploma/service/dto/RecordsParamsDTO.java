package com.diploma.service.dto;

import com.diploma.service.RecordsParams;
import com.diploma.service.SearchParameters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordsParamsDTO {
    private Long userId;
    private Integer offset;
    private Integer limit;

    public RecordsParams convertToRecordsParams() {
        RecordsParams.RecordsParamsBuilder parametersBuilder = RecordsParams.builder();
        Integer offset = this.getOffset();
        Integer limit = this.getLimit();
        if (offset != null || limit != null) {
            if (offset == null) {
                parametersBuilder.limit(limit);
            } else if (limit == null){
                parametersBuilder.offset(offset);
            } else {
                parametersBuilder
                        .offset(offset)
                        .limit(limit);
            }
        }
        return parametersBuilder.build();
    }
}
