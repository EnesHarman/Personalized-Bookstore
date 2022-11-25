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
@Table(name = "wl_message_click_event")
public class WlMessageClickEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "user_email", columnDefinition = "varchar(100)")
    private String userEmail;
    @Column(name = "wl_message_id", columnDefinition = "varchar(255)")
    private String wlMessageId;
    @Column(name = "click_date")
    private LocalDateTime clickDate;
}
