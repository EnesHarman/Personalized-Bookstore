package com.etrade.advice.repository.custom;

import com.etrade.advice.dto.AdviceListResponse;
import com.etrade.advice.model.Advice;
import com.etrade.advice.model.Product;
import com.etrade.advice.model.Segment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class AdviceRepositoryImpl implements AdviceRepositoryCustom{

    private final MongoTemplate mongoTemplate;

    public AdviceRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public AdviceListResponse getAdvicesInfo(Advice advice) {
        AdviceListResponse adviceListResponse = AdviceListResponse.builder()
                .name(advice.getName())
                .createDate(advice.getCreateDate())
                .products(findProductsOfAdvice(advice))
                .build();
        if(advice.getSegmentId() != null){
            Segment segment = mongoTemplate.findById(advice.getSegmentId(), Segment.class, "segments");
            adviceListResponse.setSegmentName(segment.getName());
        }

        return adviceListResponse;
    }

    @Override
    public Advice getUserAdviceList(String userEmail) {
        Query segmentQuery = new Query();
        segmentQuery.addCriteria(Criteria.where("userIds").regex(userEmail));
        Segment userSegment = mongoTemplate.findOne(segmentQuery, Segment.class, "segments");
        if(userSegment == null){
            return getDefaultAdvice();
        }
        Query adviceQuery = new Query();
        adviceQuery.addCriteria(Criteria.where("segmentId").is(userSegment.getId()));
        Advice adviceWithSegment = mongoTemplate.findOne(adviceQuery, Advice.class, "advice-list");
        if(adviceWithSegment == null){
            return getDefaultAdvice();
        }
        return adviceWithSegment;
    }

    private Advice getDefaultAdvice(){
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("default"));
        return mongoTemplate.findOne(query, Advice.class, "advice-list");
    }

    private List<Product> findProductsOfAdvice(Advice advice){
        Query productQuery = new Query();
        productQuery.addCriteria(Criteria.where("id").in(advice.getProductIds()));
        return  mongoTemplate.find(productQuery, Product.class, "products");
    }
}