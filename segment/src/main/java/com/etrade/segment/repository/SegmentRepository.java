package com.etrade.segment.repository;

import com.etrade.segment.dto.SegmentCreateRequest;
import com.etrade.core.model.Segment;
import com.etrade.segment.repository.custom.SegmentCustomRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SegmentRepository extends MongoRepository<Segment, String> , SegmentCustomRepository {
    Segment createSegment(SegmentCreateRequest segmentCreateRequest, long priority);

    void updateSegments(List<Segment> segments);
}
