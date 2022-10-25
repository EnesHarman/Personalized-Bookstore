package com.etrade.analytic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "product-click-event")
public class ProductClickEvent {
    @Id
    private String id;
    private String userEmail;
    private LocalDateTime clickDate;
    private String productId;
}
