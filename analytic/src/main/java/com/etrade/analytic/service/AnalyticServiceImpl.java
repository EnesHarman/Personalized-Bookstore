package com.etrade.analytic.service;

import com.etrade.analytic.core.config.kafka.events.AnalyticEvent;
import com.etrade.core.model.*;
import com.etrade.analytic.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnalyticServiceImpl implements AnalyticService{

    private LoginEventRepository loginEventRepository;
    private MessageClickEventRepository messageClickEventRepository;
    private ProductClickEventRepository productClickEventRepository;
    private ProductLinkClickEventRepository productLinkClickEventRepository;
    private WlMessageClickRepository wlMessageClickRepository;

    public AnalyticServiceImpl(LoginEventRepository loginEventRepository, MessageClickEventRepository messageClickEventRepository, ProductClickEventRepository productClickEventRepository, ProductLinkClickEventRepository productLinkClickEventRepository, WlMessageClickRepository wlMessageClickRepository) {
        this.loginEventRepository = loginEventRepository;
        this.messageClickEventRepository = messageClickEventRepository;
        this.productClickEventRepository = productClickEventRepository;
        this.productLinkClickEventRepository = productLinkClickEventRepository;
        this.wlMessageClickRepository = wlMessageClickRepository;
    }

    @Override
    public void handleLoginEvent(AnalyticEvent analyticEvent) {
        LoginEvent loginEvent = LoginEvent.builder()
                .userEmail(analyticEvent.getUserEmail())
                .loginDate(analyticEvent.getTime())
                .build();
        loginEventRepository.save(loginEvent);
        log.info("Login Event Consumed and Saved");
    }

    @Override
    public void handleProductLinkClickEvent(AnalyticEvent analyticEvent) {
        ProductLinkClickEvent productLinkClickEvent = ProductLinkClickEvent.builder()
                .userEmail(analyticEvent.getUserEmail())
                .clickDate(analyticEvent.getTime())
                .productId(analyticEvent.getProductId())
                .linkType(analyticEvent.getProductLinkType().toString())
                .build();
        productLinkClickEventRepository.save(productLinkClickEvent);
        log.info("Product Link Click Event Consumed and Saved");
    }

    @Override
    public void handleProductClickEvent(AnalyticEvent analyticEvent) {
        ProductClickEvent productClickEvent = ProductClickEvent.builder()
                .userEmail(analyticEvent.getUserEmail())
                .clickDate(analyticEvent.getTime())
                .productId(analyticEvent.getProductId())
                .build();
        productClickEventRepository.save(productClickEvent);
        log.info("Product Click Event Consumed and Saved");
    }

    @Override
    public void handleMessageClickEvent(AnalyticEvent analyticEvent) {
        MessageClickEvent messageClickEvent = MessageClickEvent.builder()
                .userEmail(analyticEvent.getUserEmail())
                .clickDate(analyticEvent.getTime())
                .messageId(analyticEvent.getMessageId())
                .build();
        messageClickEventRepository.save(messageClickEvent);
        log.info("Message Click Event Consumed and Saved");
    }

    @Override
    public void handleWlMessageClickEvent(AnalyticEvent analyticEvent) {
        WlMessageClickEvent wlMessageClickEvent = WlMessageClickEvent.builder()
                .userEmail(analyticEvent.getUserEmail())
                .wlMessageId(analyticEvent.getWlMessageId())
                .clickDate(analyticEvent.getTime())
                .build();
        wlMessageClickRepository.save(wlMessageClickEvent);
        log.info("Wl Message Click Event Consumed and Saved");
    }
}
