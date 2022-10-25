package com.etrade.analytic.service;

import com.etrade.analytic.core.config.kafka.events.AnalyticEvent;

public interface AnalyticService {
    void handleLoginEvent(AnalyticEvent analyticEvent);

    void handleProductLinkClickEvent(AnalyticEvent analyticEvent);

    void handleProductClickEvent(AnalyticEvent analyticEvent);

    void handleMessageClickEvent(AnalyticEvent analyticEvent);

    void handleWlMessageClickEvent(AnalyticEvent analyticEvent);
}
