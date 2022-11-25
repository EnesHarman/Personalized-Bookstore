package com.etrade.segment.dto;

import com.etrade.core.model.helpers.Condition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SegmentCreateRequest {
    private String name;
    private Condition condition;
}
