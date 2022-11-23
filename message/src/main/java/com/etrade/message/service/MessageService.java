package com.etrade.message.service;

import com.etrade.message.core.config.kafka.events.MessageEvent;
import com.etrade.core.result.DataResult;
import com.etrade.message.dto.MessageListRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MessageService {

    void handleMessageEvent(MessageEvent messageEvent);

    DataResult<List<MessageListRequest>> listMessages(HttpServletRequest request, int page);

    DataResult<MessageListRequest> listSingleMessage(HttpServletRequest request, String id);
}
