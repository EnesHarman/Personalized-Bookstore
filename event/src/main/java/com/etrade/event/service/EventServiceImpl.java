package com.etrade.event.service;

import com.etrade.event.core.config.kafka.events.AnalyticEvent;
import com.etrade.event.core.config.kafka.events.MessageEvent;
import com.etrade.event.core.result.*;
import com.etrade.event.dto.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class EventServiceImpl implements EventService{
    @Value(value = "${kafka.book-topic}")
    private String topicName;
    private final KafkaTemplate<String, AnalyticEvent> kafkaTemplate;

    private final KafkaTemplate<String, MessageEvent> messageKafkaTemplate;

    public EventServiceImpl(KafkaTemplate<String, AnalyticEvent> kafkaTemplate, KafkaTemplate<String, MessageEvent> messageKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.messageKafkaTemplate = messageKafkaTemplate;
    }

    @Override
    public Result sendWlMessageClickEvent(HttpServletRequest request, WlMessageClickEvent wlMessageClickEvent) {
        AnalyticEvent analyticEvent = AnalyticEvent.builder()
                .userEmail(getUserEmailFromRequest(request))
                .wlMessageId(wlMessageClickEvent.getWlMessageId())
                .time(LocalDateTime.now())
                .type(AnalyticEvent.AnalyticEventType.WL_MESSAGE_CLICK)
                .build();
        kafkaTemplate.send(topicName, analyticEvent);
        return new SuccessResult("Wl Message Click Event has sent.");
    }

    @Override
    public Result productLinkClickEvent(HttpServletRequest request, ProductLinkClickEvent productLinkClickEvent) {
        AnalyticEvent analyticEvent = AnalyticEvent.builder()
                .userEmail(getUserEmailFromRequest(request))
                .time(LocalDateTime.now())
                .productId(productLinkClickEvent.getProductId())
                .productLinkType(AnalyticEvent.castStringToType(productLinkClickEvent.getLinkType()))
                .type(AnalyticEvent.AnalyticEventType.PRODUCT_LINK_CLICK)
                .build();
        kafkaTemplate.send(topicName, analyticEvent);
        return new SuccessResult("Product Link Click Event has sent.");
    }

    @Override
    public Result productClickEvent(HttpServletRequest request, ProductClickEvent productClickEvent) {
        AnalyticEvent analyticEvent = AnalyticEvent.builder()
                .userEmail(getUserEmailFromRequest(request))
                .time(LocalDateTime.now())
                .productId(productClickEvent.getProductId())
                .type(AnalyticEvent.AnalyticEventType.PRODUCT_CLICK)
                .build();
        kafkaTemplate.send(topicName, analyticEvent);
        return new SuccessResult("Product Click Event has sent.");
    }

    @Override
    public Result messageClickEvent(HttpServletRequest request, MessageClickEvent messageClickEvent) {
        AnalyticEvent analyticEvent = AnalyticEvent.builder()
                .userEmail(getUserEmailFromRequest(request))
                .time(LocalDateTime.now())
                .messageId(messageClickEvent.getMessageId())
                .type(AnalyticEvent.AnalyticEventType.MESSAGE_CLICK)
                .build();
        kafkaTemplate.send(topicName, analyticEvent);
        return new SuccessResult("Message Click Event has sent.");
    }

    @Override
    public Result loginEvent(HttpServletRequest request) {
        AnalyticEvent analyticEvent = AnalyticEvent.builder()
                .userEmail(getUserEmailFromRequest(request))
                .time(LocalDateTime.now())
                .type(AnalyticEvent.AnalyticEventType.LOGIN)
                .build();
        kafkaTemplate.send(topicName, analyticEvent);
        return new SuccessResult("Wl Message Click Event has sent.");
    }

    @Override
    public Result sendMessage(MessageEvent messageEvent) {
        messageKafkaTemplate.send("messages-topic", messageEvent);
        return new SuccessResult("The message has been sent to users.");
    }

    private String getUserEmailFromRequest(HttpServletRequest request){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String token = request.getHeader("Authorization").split("Bearer ")[1];
        String payload = new String(decoder.decode(token.split("\\.")[1]));
        JSONObject obj = new JSONObject(payload);
        return obj.getString("email");
    }
}
