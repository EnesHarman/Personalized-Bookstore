package com.etrade.message.repository.custom;

import com.etrade.core.model.Message;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageCustomRepository {
    List<String> getUsersEmailFromTargetSegment(String segmentId);
    List<Message> listUserMessages(String email, Pageable pageable);
}
