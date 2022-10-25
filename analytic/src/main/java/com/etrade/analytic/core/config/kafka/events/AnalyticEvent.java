package com.etrade.analytic.core.config.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnalyticEvent {
    private String userEmail;
    private String messageId;
    private String wlMessageId;
    private String productId;
    private LocalDateTime time;
    private ProductLinkType productLinkType;
    private AnalyticEventType type;
    public enum AnalyticEventType{
        WL_MESSAGE_CLICK,
        MESSAGE_CLICK,
        PRODUCT_CLICK,
        PRODUCT_LINK_CLICK,
        LOGIN
    }

    public enum ProductLinkType{
        HEPSIBURADA,
        TRENDYOL
    }
}



