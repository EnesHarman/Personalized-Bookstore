package com.etrade.analytic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_link_click_event")
public class ProductLinkClickEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "user_email", columnDefinition = "varchar(100)")
    private String userEmail;
    @Column(name = "product_id", columnDefinition = "varchar(255)")
    private String productId;
    @Column(name = "link_type", columnDefinition = "varchar(100)")
    private String linkType;
    @Column(name = "click_date")
    private LocalDateTime clickDate;
}
