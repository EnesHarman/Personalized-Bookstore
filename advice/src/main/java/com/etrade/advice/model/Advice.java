package com.etrade.advice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "advice-list")
public class Advice {
    @Id
    private String id;
    private String name;
    private String segmentId;
    private LocalDateTime createDate;
    private List<String> productIds;
}
