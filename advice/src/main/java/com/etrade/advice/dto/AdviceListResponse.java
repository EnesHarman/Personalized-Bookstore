package com.etrade.advice.dto;

import com.etrade.core.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdviceListResponse {
    private String name;
    private LocalDateTime createDate;
    private String segmentName;
    private List<Product> products;
}
