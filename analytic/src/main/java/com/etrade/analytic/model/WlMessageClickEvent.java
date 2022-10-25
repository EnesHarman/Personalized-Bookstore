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
@Document(collection = "ml-message-click-event")
public class WlMessageClickEvent {
    @Id
    private String id;
    private String userEmail;
    private String wlMessageId;
    private LocalDateTime clickDate;
}
