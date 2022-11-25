package com.etrade.segment.repository.custom;

import com.etrade.core.model.User;
import com.etrade.segment.dto.SegmentCreateRequest;
import com.etrade.core.model.Segment;
import com.etrade.core.model.helpers.Condition;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SegmentRepositoryImpl implements SegmentCustomRepository{

    private final MongoTemplate mongoTemplate;

    public SegmentRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Segment createSegment(SegmentCreateRequest segmentCreateRequest) {
        Condition condition = segmentCreateRequest.getCondition();

        Segment segment = Segment.builder()
                .name(segmentCreateRequest.getName())
                .condition(condition)
                .createDate(LocalDateTime.now())
                .build();
        segment.setUserIds(getSegmentUsers(condition));
        return segment;
    }

    @Override
    public void updateSegments(List<Segment> segments) {
        for (Segment segment: segments) {
            segment.setUserIds(getSegmentUsers(segment.getCondition()));
            mongoTemplate.save(segment,"segments");
        }
    }

    private List<String> getSegmentUsers(Condition condition){
        Query query = new Query();
        if(condition.getBaseBirthDate() != null){
            query.addCriteria(Criteria.where("birthDate").gte(condition.getBaseBirthDate()));
        }
        if(condition.getTopBirthDate() != null){
            query.addCriteria(Criteria.where("birthDate").lte(condition.getTopBirthDate()));
        }
        if(condition.getGender() != null){
            query.addCriteria(Criteria.where("gender").is(condition.getGender()));
        }
        if(condition.getCity() != null){
            query.addCriteria(Criteria.where("address.city").is(condition.getCity()));
        }
        if(condition.getCountry() != null){
            query.addCriteria(Criteria.where("address.country").is(condition.getCountry()));
        }
        List<User> users = mongoTemplate.find(query, User.class , "users");
        if(condition.getPrefer() != null){
            users = users.stream().filter(user-> user.getPrefers().contains(condition.getPrefer())).collect(Collectors.toList());
        }

        return users.stream().map(User::getEmail).collect(Collectors.toList());
    }
}
