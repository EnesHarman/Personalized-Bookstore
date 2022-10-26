package com.etrade.message.repository.custom;

import com.etrade.message.model.Message;
import com.etrade.message.model.Segment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

public class MessageRepositoryImpl implements MessageCustomRepository{

    private final MongoTemplate mongoTemplate;

    public MessageRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<String> getUsersEmailFromTargetSegment(String segmentId) {
        Segment segment = mongoTemplate.findById(segmentId, Segment.class, "segments");
        return segment.getUserIds();
    }

    @Override
    public List<Message> listUserMessages(String email, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(Criteria.where("toUserEmails").regex(email));
        return mongoTemplate.find(query, Message.class, "messages");
    }
}
