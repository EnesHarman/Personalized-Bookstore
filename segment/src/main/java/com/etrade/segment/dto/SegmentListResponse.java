package com.etrade.segment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SegmentListResponse {
    private String id;
    private int userCount;
    private String name;
    private LocalDateTime createDate;
}
