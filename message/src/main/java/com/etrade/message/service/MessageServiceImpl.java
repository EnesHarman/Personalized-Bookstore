package com.etrade.message.service;

import com.etrade.message.core.config.kafka.events.MessageEvent;
import com.etrade.message.core.result.DataResult;
import com.etrade.message.core.result.ErrorDataResult;
import com.etrade.message.core.result.SuccessDataResult;
import com.etrade.message.dto.MessageListRequest;
import com.etrade.message.model.Message;
import com.etrade.message.repository.MessageRepository;
import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService{

    private MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void handleMessageEvent(MessageEvent messageEvent) {
        List<String> userEmailList = messageRepository.getUsersEmailFromTargetSegment(messageEvent.getSegmentId());
        Message message = Message.builder()
                .title(messageEvent.getTitle())
                .content(messageEvent.getContent())
                .messageType(messageEvent.getMessageType())
                .productId(messageEvent.getProductId())
                .image(messageEvent.getImage())
                .toUserEmails(userEmailList)
                .build();
        messageRepository.save(message);
    }

    @Override
    public DataResult<List<MessageListRequest>> listMessages(HttpServletRequest request, int page) {
        Pageable pageable = PageRequest.of(page - 1 ,5);
        String userEmail = getUserEmailFromRequest(request);
        List<Message> messages = messageRepository.listUserMessages(userEmail,pageable);
        List<MessageListRequest> response = messages.stream().map(this::castToListObject).collect(Collectors.toList());
        return new SuccessDataResult<>(response);
    }

    @Override
    public DataResult<MessageListRequest> listSingleMessage(HttpServletRequest request, String id) {
        String userEmail = getUserEmailFromRequest(request);
        Optional<Message> message = messageRepository.findById(id);
        if(!message.isPresent()){
            return new ErrorDataResult<>("There is no such a message with that id. Please try again later.");
        }
        if(!message.get().getToUserEmails().contains(userEmail)){
            return new ErrorDataResult<>("There is no such a message with that id.");
        }
        return new SuccessDataResult<>(castToListObject(message.get()));
    }

    private MessageListRequest castToListObject(Message message){
        return MessageListRequest.builder()
                .title(message.getTitle())
                .content(message.getContent())
                .messageType(message.getMessageType())
                .productId(message.getProductId())
                .image(message.getImage())
                .build();
    }

    private String getUserEmailFromRequest(HttpServletRequest request){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String token = request.getHeader("Authorization").split("Bearer ")[1];
        String payload = new String(decoder.decode(token.split("\\.")[1]));
        JSONObject obj = new JSONObject(payload);
        return obj.getString("email");
    }
}
