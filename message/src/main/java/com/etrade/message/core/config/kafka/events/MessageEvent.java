package com.etrade.message.core.config.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageEvent {
    private String title;
    private String content;
    private String messageType;
    private String productId;
    private String image;
    private String segmentId;
}
