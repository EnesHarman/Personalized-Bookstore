package com.etrade.event.controller;

import com.etrade.event.core.config.kafka.events.MessageEvent;
import com.etrade.event.core.result.Result;
import com.etrade.event.dto.*;
import com.etrade.event.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/wl-message-click")
    public ResponseEntity<String> sendWlMessageClickEvent(HttpServletRequest request, @RequestBody WlMessageClickEvent wlMessageClickEvent){
        Result result = eventService.sendWlMessageClickEvent(request, wlMessageClickEvent);
        return result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.internalServerError().body(result.getMessage());
    }

    @PostMapping("/product-link-click")
    public ResponseEntity<String> productLinkClickEvent(HttpServletRequest request, @RequestBody ProductLinkClickEvent productLinkClickEvent){
        Result result = eventService.productLinkClickEvent(request, productLinkClickEvent);
        return result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.internalServerError().body(result.getMessage());
    }

    @PostMapping("/product-click")
    public ResponseEntity<String> productClickEvent(HttpServletRequest request, @RequestBody ProductClickEvent productClickEvent){
        Result result = eventService.productClickEvent(request, productClickEvent);
        return result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.internalServerError().body(result.getMessage());
    }

    @PostMapping("/message-click")
    public ResponseEntity<String> messageClickEvent(HttpServletRequest request, @RequestBody MessageClickEvent messageClickEvent){
        Result result = eventService.messageClickEvent(request, messageClickEvent);
        return result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.internalServerError().body(result.getMessage());
    }

    @PostMapping("/login-event")
    public ResponseEntity<String> loginEvent(HttpServletRequest request){
        Result result = eventService.loginEvent(request);
        return result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.internalServerError().body(result.getMessage());
    }

    @PostMapping("/send-message")
    public ResponseEntity<String> sendMessageEvent(@RequestBody MessageEvent messageEvent){
        Result result = eventService.sendMessage(messageEvent);
        return result.isSuccess() ? ResponseEntity.ok(result.getMessage()) : ResponseEntity.internalServerError().body(result.getMessage());
    }

}
