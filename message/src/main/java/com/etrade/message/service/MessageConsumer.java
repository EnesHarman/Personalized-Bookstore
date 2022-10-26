package com.etrade.message.service;

import com.etrade.message.core.config.kafka.events.MessageEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {
    private MessageService messageService;

    public MessageConsumer(MessageService messageService) {
        this.messageService = messageService;
    }
    @KafkaListener(topics = "messages-topic")
    public void listenMessageEvent(MessageEvent messageEvent){
        messageService.handleMessageEvent(messageEvent);
    }
}
