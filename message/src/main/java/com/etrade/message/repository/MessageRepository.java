package com.etrade.message.repository;

import com.etrade.core.model.Message;
import com.etrade.message.repository.custom.MessageCustomRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String>, MessageCustomRepository {
    List<String> getUsersEmailFromTargetSegment(String segmentId);

    List<Message> listUserMessages(String email, Pageable pageable);

}
