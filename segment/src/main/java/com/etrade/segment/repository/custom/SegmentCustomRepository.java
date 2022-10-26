package com.etrade.segment.repository.custom;

import com.etrade.segment.dto.SegmentCreateRequest;
import com.etrade.segment.model.Segment;

import java.util.List;

public interface SegmentCustomRepository {
    Segment createSegment(SegmentCreateRequest segmentCreateRequest);
    void updateSegments(List<Segment> segments);
}
