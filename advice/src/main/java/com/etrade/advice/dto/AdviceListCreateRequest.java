package com.etrade.advice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdviceListCreateRequest {
    private String name;
    private String segmentId;
    private List<String> productIds;
}
