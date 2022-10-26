package com.etrade.segment.repository;

import com.etrade.segment.dto.SegmentCreateRequest;
import com.etrade.segment.model.Segment;
import com.etrade.segment.repository.custom.SegmentCustomRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SegmentRepository extends MongoRepository<Segment, String> , SegmentCustomRepository {
    Segment createSegment(SegmentCreateRequest segmentCreateRequest);

    void updateSegments(List<Segment> segments);
}
