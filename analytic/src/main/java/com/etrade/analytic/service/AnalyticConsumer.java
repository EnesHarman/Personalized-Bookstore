package com.etrade.analytic.service;

import com.etrade.analytic.core.config.kafka.events.AnalyticEvent;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AnalyticConsumer {

    private final AnalyticService analyticService;

    public AnalyticConsumer(AnalyticService analyticService) {
        this.analyticService = analyticService;
    }

    @KafkaListener(topics = "analytic-topic")
    public void listenAnalytics(AnalyticEvent analyticEvent){
        if(analyticEvent.getType() == AnalyticEvent.AnalyticEventType.LOGIN){
            analyticService.handleLoginEvent(analyticEvent);
        } else if(analyticEvent.getType() == AnalyticEvent.AnalyticEventType.PRODUCT_LINK_CLICK){
            analyticService.handleProductLinkClickEvent(analyticEvent);
        } else if(analyticEvent.getType() == AnalyticEvent.AnalyticEventType.PRODUCT_CLICK){
            analyticService.handleProductClickEvent(analyticEvent);
        } else if(analyticEvent.getType() == AnalyticEvent.AnalyticEventType.MESSAGE_CLICK){
            analyticService.handleMessageClickEvent(analyticEvent);
        } else if(analyticEvent.getType() == AnalyticEvent.AnalyticEventType.WL_MESSAGE_CLICK){
            analyticService.handleWlMessageClickEvent(analyticEvent);
        }
    }
}
