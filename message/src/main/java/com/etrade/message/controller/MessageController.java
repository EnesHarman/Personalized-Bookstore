package com.etrade.message.controller;

import com.etrade.core.result.DataResult;
import com.etrade.message.dto.MessageListRequest;
import com.etrade.message.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<MessageListRequest>> listMessages(HttpServletRequest request, @RequestParam(defaultValue = "0") int page){
        DataResult<List<MessageListRequest>> result = messageService.listMessages(request, page);
        return result.isSuccess() ? ResponseEntity.ok(result.getData()) : ResponseEntity.internalServerError().body(result.getData());
    }

    @GetMapping("/list-single/{id}")
    public ResponseEntity<MessageListRequest> listSingleMessages(HttpServletRequest request, @PathVariable String id){
        DataResult<MessageListRequest> result = messageService.listSingleMessage(request, id);
        return result.isSuccess() ? ResponseEntity.ok(result.getData()) : ResponseEntity.internalServerError().body(result.getData());
    }

}
